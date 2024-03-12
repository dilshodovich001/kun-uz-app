package uz.kun.application.services;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.kun.application.models.exception.APIException;
import uz.kun.domain.entities.ArticleType;
import uz.kun.domain.models.request.ArticleTypeRequest;
import uz.kun.domain.models.response.ArticleTypeResponse;
import uz.kun.domain.usecases.ArticleTypeUseCase;
import uz.kun.infrastructure.jpa.repository.ArticleTypeJpaRepository;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class ArticleTypeService implements ArticleTypeUseCase {

    private final ArticleTypeJpaRepository articleTypeJpaRepository;

    @Override
    public void create(ArticleTypeRequest articleTypeRequest) {
        var optionalArticleType = articleTypeJpaRepository.findByKey(articleTypeRequest.key());
        if (optionalArticleType.isPresent()) throw new APIException("Article type exist...", 400);

        var articleTypeEntity = articleTypeRequest.mapToEntity();
        articleTypeJpaRepository.save(articleTypeEntity);
    }

    @Override
    public ArticleTypeResponse read(Integer articleTypeId) {
        return articleTypeJpaRepository
                .findById(articleTypeId)
                .map(ArticleType::mapToResponse)
                .orElseThrow(() -> new APIException("Article type not found...", 404));
    }

    @Override
    public ArticleTypeResponse update(
            Integer articleTypeId,
            ArticleTypeRequest articleTypeRequest
    ) {
        var articleTypeEntity = articleTypeJpaRepository
                .findById(articleTypeId)
                .orElseThrow(() -> new APIException("Article type not found...", 404));

        articleTypeEntity.setKey(articleTypeRequest.key());
        articleTypeEntity.setNameUz(articleTypeRequest.nameUz());
        articleTypeEntity.setNameRu(articleTypeRequest.nameRu());
        articleTypeEntity.setNameEn(articleTypeRequest.nameEn());

        articleTypeJpaRepository.save(articleTypeEntity);
        return articleTypeEntity.mapToResponse();
    }

    @Override
    public void delete(Integer articleTypeId) {
        articleTypeJpaRepository.deleteById(articleTypeId);
    }

    @Override
    public List<ArticleTypeResponse> articleTypesList() {
        return articleTypeJpaRepository
                .findAll()
                .stream()
                .map(ArticleType::mapToResponse)
                .toList();
    }
}
