package uz.kun.application.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.kun.application.models.rest.ResponseData;
import uz.kun.domain.models.request.AttachRequest;
import uz.kun.domain.models.response.AttachResponse;
import uz.kun.domain.usecases.AttachUseCase;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attach")
public class AttachController {

    private final AttachUseCase attachUseCase;


    @PostMapping("/upload")
    public ResponseData<AttachResponse> uploadFile(
            @RequestParam("file")
            MultipartFile multipartFile
    ) throws IOException {

        var attachResponse = attachUseCase.uploadFile(new AttachRequest(
                multipartFile.getOriginalFilename(),
                multipartFile.getBytes(),
                multipartFile.getSize()
        ));

        return ResponseData.ok(attachResponse);
    }

    @GetMapping("/{fileId}")
    public ResponseData<AttachResponse> getFile(@PathVariable String fileId) {
        var attachResponse = attachUseCase.getFile(fileId);
        return ResponseData.ok(attachResponse);
    }

    @GetMapping("/open")
    public ResponseEntity<byte[]> openImage(@RequestParam("file") String fileName) {
        var fileResponseBytes = attachUseCase.open(fileName);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(fileResponseBytes);
    }

    @DeleteMapping("/{fileName}")
    public void delete(@PathVariable String fileName) {
        attachUseCase.delete(fileName);
    }
}
