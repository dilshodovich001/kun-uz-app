package uz.kun.tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.kun.config.Language;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tag")
public class TagController {
    private final TagService tagService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TagResponse> create(
            @RequestBody @Valid TagRequest request
    ) {
        log.info("Request to create a tag: {}", request.toString());
        return ResponseEntity.ok(tagService.create(request));
    }

    @GetMapping("/list")
    public ResponseEntity<List<TagResponse>> getList(@RequestParam("lang") Language lang) {
        log.info("Tag list request in {}", lang.name());
        return ResponseEntity.ok(tagService.getList(lang));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<TagResponse> update(
            @RequestParam("id") Integer id,
            @RequestBody TagRequest request
    ) {
        log.info("Update request: {}, id: {}", request.toString(), id);
        return ResponseEntity.ok(tagService.update(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam("id") Integer id) {
        log.info("Delete request, id: {}", id);
        boolean result = tagService.delete(id);

        if (result) log.info("Tag successfully deleted !");
        return ResponseEntity.ok("Tag successfully deleted !");
    }

}
