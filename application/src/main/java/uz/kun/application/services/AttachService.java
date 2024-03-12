package uz.kun.application.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.kun.application.models.exception.APIException;
import uz.kun.domain.entities.Attach;
import uz.kun.domain.models.request.AttachRequest;
import uz.kun.domain.models.response.AttachResponse;
import uz.kun.domain.usecases.AttachUseCase;
import uz.kun.infrastructure.jpa.repository.AttachJpaRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static java.time.LocalDateTime.now;

@Service
@Transactional
@RequiredArgsConstructor
public class AttachService implements AttachUseCase {

    private final AttachJpaRepository attachJpaRepository;

    @Value("${attach-uri}")
    private String attachUri;

    @Override
    public AttachResponse uploadFile(AttachRequest attachRequest) {
        String fileUploadDir = "attach/" + getYearMonthDay();

        var directory = new File(fileUploadDir);
        if (!directory.exists()) directory.mkdirs();

        try {

            Path path = Paths.get(fileUploadDir + attachRequest.filename());
            Files.write(path, attachRequest.bytes());

            var attach = attachRequest.mapToEntity();
            attach.setUrl("%s%s".formatted(attachUri, attachRequest.filename()));
            attach.setPath("%s%s".formatted(fileUploadDir, attachRequest.filename()));

            attachJpaRepository.save(attach);
            return attach.mapToResponse();
        } catch (IOException e) {
            throw new APIException("Internal server error...", 500);
        }
    }

    private String getYearMonthDay() {
        return String.format("%s/%s/%s/", now().getYear(), now().getMonthValue(), now().getDayOfMonth());
    }

    @Override
    public AttachResponse getFile(String fileId) {
        return attachJpaRepository
                .findById(fileId)
                .map(Attach::mapToResponse)
                .orElseThrow(() -> new APIException("Attach not found...", 404));
    }

    @Override
    public byte[] open(String fileName) {
        var optionalAttach = attachJpaRepository.findByFilename(fileName);
        if (optionalAttach.isEmpty()) throw new APIException("Attach not found...", 404);

        try {

            var attachEntity = optionalAttach.get();
            System.out.println("attachEntity = " + attachEntity);
            return Files.readAllBytes(new File(attachEntity.getPath()).toPath());

        } catch (IOException e) {
            throw new APIException("Internal server error...", 500);
        }
    }

    @Override
    public void delete(String fileName) {
        var attachEntity = attachJpaRepository
                .findByFilename(fileName)
                .orElseThrow(() -> new APIException("Attach not found...", 404));

        try {
            if (Files.deleteIfExists(new File(attachEntity.getPath()).toPath())) {
                attachJpaRepository.delete(attachEntity);
            }
        } catch (IOException e) {
            throw new APIException("Internal server error...", 500);
        }
    }
}
