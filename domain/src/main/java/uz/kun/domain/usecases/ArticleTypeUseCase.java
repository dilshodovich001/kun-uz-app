package uz.kun.domain.usecases;

import uz.kun.domain.models.request.ArticleTypeRequest;
import uz.kun.domain.models.response.ArticleTypeResponse;

import java.util.List;

public interface ArticleTypeUseCase extends CrudUseCase<Integer, ArticleTypeRequest, ArticleTypeResponse> {
    List<ArticleTypeResponse> articleTypesList();
}