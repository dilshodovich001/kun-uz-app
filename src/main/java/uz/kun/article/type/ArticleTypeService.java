package uz.kun.article.type;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.kun.config.Language;
import uz.kun.config.DetailService;
import uz.kun.exception.ItemAlreadyExistsException;
import uz.kun.exception.ItemNotFoundException;
import uz.kun.user.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleTypeService {
    private final ArticleTypeRepository articleTypeRepository;

    /** CREATE **/
    public ArticleTypeResponse create(ArticleTypeRequest request) {
        var articleTypeExists = articleTypeRepository.findByKey(request.getKey()).isPresent();

        if (articleTypeExists)
            throw new ItemAlreadyExistsException("ArticleType already exists, key: " + request.getKey());

        var articleType = toEntity(request);
        articleTypeRepository.save(articleType);

        return toResponse(articleType);
    }

    /** READ **/
    public List<ArticleTypeResponse> getList() {
        return articleTypeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ArticleTypeResponse> getList(Language lang) {
        return articleTypeRepository.findAll()
                .stream()
                .map(e -> this.getById(e.getId(), lang))
                .toList();
    }

    public ArticleTypeResponse getById(Integer id) {
        var article = get(id);
        return toResponse(article);
    }

    public ArticleTypeResponse getById(Integer id, Language lang) {
        var article = get(id);
        var response = toResponse(article);

        switch (lang) {
            case uz -> response.setName(article.getNameUz());
            case ru -> response.setName(article.getNameRu());
            case en -> response.setName(article.getNameEn());
        }

        return response;
    }

    public ArticleTypeResponse getByKey(String key) {
        var article = get(key);
        return toResponse(article);
    }

    /** UPDATE **/
    public ArticleTypeResponse update(Integer id, ArticleTypeRequest request) {
        var articleType = get(id);

        articleType.setKey(request.getKey());
        articleType.setNameRu(request.getNameRu());
        articleType.setNameEn(request.getNameEn());
        articleType.setNameUz(request.getNameUz());
        articleType.setUpdatedDate(LocalDateTime.now());

        articleTypeRepository.save(articleType);
        return toResponse(articleType);
    }

    /** DELETE **/
    public boolean delete(Integer id) {
        var articleType = get(id);
        articleTypeRepository.delete(articleType);

        return true;
    }

    public boolean delete(String key) {
        var articleType = get(key);
        articleTypeRepository.delete(articleType);

        return true;
    }

    private ArticleTypeResponse toResponse(ArticleTypeEntity e) {
        return ArticleTypeResponse.builder()
                .id(e.getId())
                .key(e.getKey())
                .nameUz(e.getNameUz())
                .nameEn(e.getNameEn())
                .nameRu(e.getNameRu())
                .creatorId(e.getCreatorId())
                .createdDate(e.getCreatedDate())
                .updatedDate(e.getUpdatedDate())
                .build();
    }

    private ArticleTypeEntity toEntity(ArticleTypeRequest request) {
        UserEntity creator = DetailService.getUser();
        log.info("{} successfully created article type !", creator.toString());

        return ArticleTypeEntity.builder()
                .key(request.getKey())
                .nameUz(request.getNameUz())
                .nameRu(request.getNameRu())
                .nameEn(request.getNameEn())
                .creatorId(creator.getId())
                .user(creator)
                .createdDate(LocalDateTime.now())
                .build();
    }

    private ArticleTypeEntity get(Integer id) {
        return articleTypeRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Article type not found, id: " + id));
    }

    private ArticleTypeEntity get(String key) {
        return articleTypeRepository.findByKey(key)
                .orElseThrow(() -> new ItemNotFoundException("Article type not found, key: " + key));
    }
}
