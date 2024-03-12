package uz.kun.application.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.domain.models.request.ArticleTypeRequest;
import uz.kun.domain.models.response.ArticleTypeResponse;
import uz.kun.application.services.ArticleTypeService;
import uz.kun.infrastructure.config.Language;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/article-type")
public class ArticleTypeController {
    private final ArticleTypeService articleTypeService;

    /**======== PUBLIC REQUESTS ========*/
    @GetMapping("/public/list")
    public ResponseEntity<List<ArticleTypeResponse>> getList(@RequestParam("lang") Language lang) {
        return ResponseEntity.ok(articleTypeService.getList(lang));
    }
    @GetMapping("/public")
    public ResponseEntity<ArticleTypeResponse> getById(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(articleTypeService.getById(id));
    }

    /**======== PUBLIC REQUESTS END ========*/


    /**======== PRIVATE REQUESTS ========*/
    @PostMapping("/private/create")
    public ResponseEntity<ArticleTypeResponse> create(
            @RequestBody @Valid ArticleTypeRequest request
    ) {
        log.info("ArticleType request: {}", request.toString());
        return ResponseEntity.ok(articleTypeService.create(request));
    }

    @PutMapping("/private/update")
    public ResponseEntity<ArticleTypeResponse> update(
            @RequestParam("id") Integer id,
            @RequestBody ArticleTypeRequest request
    ) {
        log.info("Article type update request: {}, id: {}", request.toString(), id);
        return ResponseEntity.ok(articleTypeService.update(id, request));
    }
    @DeleteMapping("/private/delete")
    public ResponseEntity<String> deleteById(@RequestParam("id") Integer id){
        log.info("Article type delete request, id: {}", id);
        boolean result = articleTypeService.delete(id);

        if (result) log.info("Article type successfully deleted !");
        return ResponseEntity.ok("Article type successfully deleted !");
    }
    /**======== PRIVATE REQUESTS ========*/

}
