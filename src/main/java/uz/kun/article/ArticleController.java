package uz.kun.article;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.config.Language;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
public class ArticleController {
    private final ArticleService articleService;

    /**======== PUBLIC REQUESTS ========*/

    @GetMapping("/public/{lang}/{articleId}")
    public ResponseEntity<ArticleResponse> getById(
            @PathVariable Language lang,
            @PathVariable Integer articleId
    ) {
        log.info("Article get request, id: {}, language: {}", articleId, lang.name());
        return ResponseEntity.ok(articleService.getById(articleId, lang));
    }

    @GetMapping("/public/{lang}/region")
    public ResponseEntity<List<ArticleResponse>> getByRegionId(
            @PathVariable Language lang,
            @RequestParam Integer regionId,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        return ResponseEntity.ok(articleService.getByRegionId(lang, regionId, page, size));
    }

    @GetMapping("/public/{lang}/category")
    public ResponseEntity<List<ArticleResponse>> getByCategoryId(
            @PathVariable Language lang,
            @RequestParam Integer categoryId,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        return ResponseEntity.ok(articleService.getByCategoryId(lang, categoryId, page, size));
    }

    @GetMapping("/public/{lang}/type")
    public ResponseEntity<List<ArticleResponse>> getByTypeId(
            @PathVariable Language lang,
            @RequestParam Integer typeId,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        return ResponseEntity.ok(articleService.getByTypeId(lang, typeId, page, size));
    }

    @GetMapping("/public/{lang}/list")
    public ResponseEntity<List<ArticleResponse>> getList(
            @PathVariable Language lang,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(articleService.getList(page, size, lang));
    }

    @GetMapping("/public/{lang}/top-five")
    public ResponseEntity<List<ArticleResponse>> getByType(
            @PathVariable Language lang,
            @RequestParam Integer typeId
    ) {
        return ResponseEntity.ok(articleService.getTopFiveArticle(typeId, lang));
    }

    @GetMapping("/public/{lang}/last-article")
    public ResponseEntity<List<ArticleResponse>> getLastAddedFourArticle(
            @PathVariable Language lang
    ) {
        return ResponseEntity.ok(articleService.getLastAddedFourArticle(lang));
    }

    @GetMapping("/public/{lang}/top-four-article")
    public ResponseEntity<List<ArticleResponse>> getByRegionIdTopFourArticle(
            @PathVariable Language lang,
            @RequestParam Integer regionId
    ) {
        return ResponseEntity.ok(articleService.getByRegionIdTopFourArticle(lang, regionId));
    }

    @GetMapping("/public/{lang}/share")
    public ResponseEntity<?> generateShareLink(
            @PathVariable Language lang,
            @RequestParam Integer articleId
    ) {
        return ResponseEntity.ok(articleService.generateShareLink(lang, articleId));
    }

    @GetMapping("/public/view")
    public ResponseEntity<?> increaseViewCount(
            @RequestParam Integer articleId
    ) {
        articleService.updateViewCount(articleId);
        return ResponseEntity.ok().build();
    }


    /**======== PUBLIC REQUESTS ========*/


    /**======== PRIVATE REQUESTS ========*/

    @PostMapping("/private/create")
    public ResponseEntity<ArticleResponse> create(
            @RequestBody @Valid ArticleRequest request
    ) {
        log.info("Request to create an article: {}", request.toString());
        return ResponseEntity.ok(articleService.create(request));
    }


    @PutMapping("/private/{lang}/update")
    public ResponseEntity<ArticleResponse> update(
            @PathVariable Language lang,
            @RequestParam Integer articleId,
            @Valid ArticleRequest request
    ) {
        log.info("Article update request: {}, id: {}", request.toString(), articleId);
        return ResponseEntity.ok(articleService.update(lang, articleId, request));
    }

    @PutMapping("/private/change-status")
    public ResponseEntity<?> changeStatus(
            @RequestParam Integer articleId,
            @RequestParam ArticleStatus status
    ) {
        return ResponseEntity.ok(articleService.changeStatus(articleId, status));
    }

    @DeleteMapping("/private/delete")
    public ResponseEntity<String> delete(@RequestParam Integer articleId) {
        log.info("Article delete request articleId: " + articleId);
        boolean result = articleService.delete(articleId);
        return ResponseEntity.ok("Article successfully deleted: " + result);
    }

    /**======== PRIVATE REQUESTS END ========*/

}
