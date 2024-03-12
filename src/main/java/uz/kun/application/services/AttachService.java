package uz.kun.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.kun.domain.models.response.AttachResponse;
import uz.kun.domain.entities.AttachEntity;
import uz.kun.infrastructure.jpa.repository.AttachRepository;
import uz.kun.domain.exception.AttachException;
import uz.kun.domain.exception.ItemNotFoundException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachService {
    private final AttachRepository attachRepository;
    @Value("${attach.upload.folder}")
    private String attachFolder;
    @Value("${attach.open.url}")
    private String attachOpenUrl;

    public AttachResponse saveToSystem(MultipartFile request) {
        try {
            // attaches/2022/04/23/UUID.png
            String attachPath = getYmDString(); // 2022/04/23
            String extension = getExtension(request.getOriginalFilename()); // .png....
            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + "." + extension; // UUID.png

            File folder = new File(attachFolder + attachPath);  // attaches/2022/04/23/
            if (!folder.exists()) folder.mkdirs();

            byte[] bytes = request.getBytes();

            Path path = Paths.get(attachFolder + attachPath + "/" + fileName); // attaches/2022/04/23/UUID.png
            Files.write(path, bytes);

            var attachEntity = AttachEntity.builder()
                    .id(uuid)
                    .originalName(request.getOriginalFilename())
                    .path(attachPath)
                    .size(request.getSize())
                    .extension(extension)
                    .createdDate(LocalDateTime.now())
                    .build();

            attachRepository.save(attachEntity);

            var attachDto = toDto(attachEntity);
            attachDto.setPath(attachOpenUrl + fileName);

            return attachDto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private AttachResponse toDto(AttachEntity e) {

        return AttachResponse.builder()
                .id(e.getId())
                .originalName(e.getOriginalName())
                .path(e.getPath())
                .size(e.getSize())
                .url(attachOpenUrl + e.getId())
                .extension(e.getExtension())
                .createdDate(e.getCreatedDate())
                .build();
    }

    public String getYmDString() {
        return String.format("%s/%s/%s",
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getDayOfMonth()
        ); // 2022/04/23
    }

    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public byte[] loadImage(String fileName) {
        if (fileName == null || fileName.length() < 1)
            throw new AttachException("file is null or length less than 1");

        var attachEntity = get(fileName);
        var path = attachEntity.getPath();
        byte[] imageInByte;
        BufferedImage originalImage;

        try {
            originalImage = ImageIO.read(new File(attachFolder + path + "/" + fileName + "." + attachEntity.getExtension()));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, attachEntity.getExtension(), baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public AttachEntity get(String fileName) {
        return attachRepository.findById(fileName).orElseThrow(() -> {
            throw new ItemNotFoundException("Attach not found");
        });
    }

    public AttachResponse getById(String id) {
        var attach = attachRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Attach not found"));

        return toDto(attach);
    }

    public byte[] openGeneral(String fileName) {
        if (fileName == null || fileName.length() < 1)
            throw new AttachException("file is null or length less than 1");

        var attachEntity = get(fileName);
        var path = attachEntity.getPath();
        byte[] data;

        try {

            Path file = Paths.get(attachFolder + path + "/" + fileName + "." + attachEntity.getExtension());
            data = Files.readAllBytes(file);
            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

    public ResponseEntity<Resource> download(String fileName) {
        var attachEntity = get(fileName);
        if (attachEntity == null) return null;

        var path = String.format("%s/%s.%s",
                attachEntity.getPath(),
                fileName,
                attachEntity.getExtension());

        var file = Paths.get(attachFolder + path);

        try {

            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "important; filename=\"" + attachEntity.getOriginalName() + "\"")
                        .body(resource);

            } else {
                throw new RuntimeException("Could not read the file!");
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }

    public PageImpl<AttachResponse> getList(Integer page, Integer size) {

        var pageRequest = PageRequest.of(page, size);
        var attachList = attachRepository.findAll(pageRequest);

        if (attachList.isEmpty()) return null;
        var dtoList = attachList.map(this::toDto).toList();

        return new PageImpl<>(
                dtoList,
                pageRequest,
                attachList.getTotalElements()
        );
    }

    public String delete(String id) {
        var attach = attachRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("Attach not found with id: " + id));

        attachRepository.delete(attach);

        var path = attachFolder + attach.getPath();
        path = String.format("%s/%s.%s",path,id,attach.getExtension());

        File file = new File(path);
        file.delete();

        return "Attach successfully deleted !";
    }

}