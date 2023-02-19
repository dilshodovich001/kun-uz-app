package uz.kun.attach;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attach")
public class AttachController {
    private final AttachService attachService;

    /**======== PUBLIC REQUESTS ========*/
    @GetMapping(value = "/public/load/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] loadImage(@PathVariable String fileName) {
        log.info("Loading file: {}", fileName);
        return attachService.loadImage(fileName);
    }

    @GetMapping(value = "/public/open/{fileName}", produces = MediaType.ALL_VALUE)
    public byte[] openGeneral(@PathVariable String fileName) {
        log.info("open file: {}", fileName);
        return attachService.openGeneral(fileName);
    }

    @GetMapping("/public/download/{fileName}")
    public ResponseEntity<?> download(@PathVariable String fileName){
        log.info("Downloading file: {}", fileName);
        return attachService.download(fileName);
    }
    /**======== PUBLIC REQUESTS EMD ========*/



    /**======== PRIVATE REQUESTS ========*/
    @PostMapping("/private/upload")
    public ResponseEntity<AttachDto> upload(@RequestBody @NonNull MultipartFile request){
        log.info("MultipartFile request: {}", request.toString());
        return ResponseEntity.ok(attachService.saveToSystem(request));
    }
    @GetMapping ("/private/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listAdmin(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) {
        return ResponseEntity.ok(attachService.getList(page,size));
    }

    @DeleteMapping("/private/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete( @PathVariable("id") String id) {
        return ResponseEntity.ok(attachService.delete(id));
    }
    /**======== PRIVATE REQUESTS END ========*/
}