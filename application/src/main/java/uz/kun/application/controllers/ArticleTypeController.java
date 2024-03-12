package uz.kun.application.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.kun.application.models.rest.ResponseData;
import uz.kun.domain.models.request.ArticleTypeRequest;
import uz.kun.domain.models.response.ArticleTypeResponse;
import uz.kun.domain.usecases.ArticleTypeUseCase;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/article-types")
public class ArticleTypeController {

    private final ArticleTypeUseCase articleTypeUseCase;

    @PostMapping
    public void create(@RequestBody ArticleTypeRequest articleTypeRequest) {
        articleTypeUseCase.create(articleTypeRequest);
    }

    @GetMapping
    public ResponseData<ArticleTypeResponse> read(
            @RequestParam("article-type-id")
            Integer articleTypeId
    ) {
        return ResponseData.ok(articleTypeUseCase.read(articleTypeId));
    }

    @GetMapping("/list")
    public ResponseData<List<ArticleTypeResponse>> articleTypesList() {
        return ResponseData.ok(articleTypeUseCase.articleTypesList());
    }

    @PutMapping
    public ResponseData<ArticleTypeResponse> update(
            @RequestParam("article-type-id") Integer articleTypeId,
            @RequestBody ArticleTypeRequest articleTypeRequest
    ) {
        return ResponseData.ok(articleTypeUseCase.update(articleTypeId, articleTypeRequest));
    }

    @DeleteMapping
    public void delete(@RequestParam("article-type-id") Integer articleTypeId) {
        articleTypeUseCase.delete(articleTypeId);
    }
}
