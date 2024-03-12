package uz.kun.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.kun.infrastructure.config.Language;
import uz.kun.domain.constants.ArticleStatus;
import uz.kun.domain.entities.ArticleEntity;
import uz.kun.domain.models.request.ArticleRequest;
import uz.kun.domain.models.response.ArticleResponse;
import uz.kun.domain.exception.ItemAlreadyExistsException;
import uz.kun.domain.exception.ItemNotFoundException;
import uz.kun.infrastructure.jpa.repository.ArticleRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleTypeService articleTypeService;
    private final TagService tagService;
    private final UserService userService;
    private final AttachService attachService;
    private final RegionService regionService;
    private final CategoryService categoryService;

    private ArticleResponse toResponse(ArticleEntity e, Language lang) {
        return ArticleResponse.builder()
                .id(e.getId())
                .title(e.getTitle())
                .description(e.getDescription())
                .content(e.getContent())
                .visible(e.getVisible())
                .status(e.getStatus())
                .publishedDate(e.getPublishedDate())
                .creator(userService.getById(e.getCreatorId()))
                .attach(attachService.getById(e.getAttachId()))
                .region(regionService.getRegion(e.getRegionId(), lang))
                .articleType(articleTypeService.getById(e.getTypeId(), lang))
                .category(categoryService.getById(e.getCategoryId(), lang))
                .tagList(tagService.getTagList(e.getTagList(), lang))
                .viewCount(e.getViewCount())
                .sharedCount(e.getSharedCount())
                .updatedDate(e.getUpdatedDate())
                .createdDate(e.getCreatedDate())
                .build();
    }

    private ArticleEntity toEntity(ArticleRequest request) {
        return ArticleEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .content(request.getContent())
                .creatorId(request.getCreatorId())
                .attachId(request.getAttachId())
                .regionId(request.getRegionId())
                .typeId(request.getTypeId())
                .categoryId(request.getCategoryId())
                .tagList(request.getTagList())
                .status(ArticleStatus.CREATED)
                .createdDate(LocalDateTime.now())
                .build();
    }

    public ArticleEntity get(Integer id) {
        var article = articleRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Article not found, id: " + id));

        if (!article.getVisible()) throw new ItemNotFoundException("Article not found, id: " + id);
        return article;
    }

    public ArticleResponse create(ArticleRequest request) {
        var articleExists = articleRepository.findByTitle(request.getTitle()).isPresent();
        if (articleExists)
            throw new ItemAlreadyExistsException("Article already exists, title: " + request.getTitle());

        ArticleEntity article = toEntity(request);
        articleRepository.save(article);

        return toResponse(article, Language.uz);
    }

    public ArticleResponse getById(Integer id, Language lang) {
        var article = get(id);
        return toResponse(article, lang);
    }

    public boolean delete(Integer id) {
        var article = get(id);
        article.setVisible(false);

        articleRepository.save(article);
        return true;
    }

    public ArticleResponse update(Language lang, Integer id, ArticleRequest request) {
        var article = get(id);

        article.setTitle(request.getTitle());
        article.setDescription(request.getDescription());
        article.setContent(request.getContent());
        article.setUpdatedDate(LocalDateTime.now());

        articleRepository.save(article);
        return toResponse(article, lang);
    }

    public List<ArticleResponse> getList(int page, int size, Language lang) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));

        return articleRepository.findByVisible(true, pageable)
                .map(e -> this.toResponse(e, lang))
                .toList();
    }

    public List<ArticleResponse> getTopFiveArticle(int typeId, Language lang) {
        return articleRepository.getTypeId(typeId, ArticleStatus.PUBLISHED.name()).stream()
                .map(e -> this.toResponse(e, lang))
                .toList();
    }

    public List<ArticleResponse> getByRegionId(Language lang, Integer regionId, Integer page, Integer size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC));
        return articleRepository.findByRegionId(regionId, true, pageable)
                .stream()
                .map(e -> this.toResponse(e, lang))
                .toList();
    }

    public List<ArticleResponse> getByCategoryId(Language lang, Integer categoryId, Integer page, Integer size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC));
        return articleRepository.findByCategoryId(categoryId, true, pageable)
                .stream()
                .map(e -> this.toResponse(e, lang))
                .toList();
    }

    public List<ArticleResponse> getByTypeId(Language lang, Integer typeId, Integer page, Integer size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC));
        return articleRepository.findByTypeId(typeId, true, pageable)
                .stream()
                .map(e -> this.toResponse(e, lang))
                .toList();
    }

    public List<ArticleResponse> getLastAddedFourArticle(Language lang) {
        return articleRepository.findByLastAddedFourArticle(ArticleStatus.PUBLISHED.name())
                .stream()
                .map(e -> this.toResponse(e, lang))
                .toList();
    }

    public List<ArticleResponse> getByRegionIdTopFourArticle(Language lang, Integer regionId) {
        return articleRepository.findByRegionIdTopFourArticle(regionId, ArticleStatus.PUBLISHED.name())
                .stream()
                .map(e -> this.toResponse(e, lang))
                .toList();
    }

    public String generateShareLink(Language lang, Integer articleId) {
        articleRepository.updateSharedCount(articleId);
        return "http://localhost:8080/article/"+lang.name()+"/"+articleId;
    }

    public void updateViewCount(Integer articleId) {
        articleRepository.updateViewCount(articleId);
    }

    public boolean changeStatus(Integer articleId,ArticleStatus status) {
        var article = get(articleId);
        if (article.getStatus().equals(status)) return false;
        article.setStatus(status);
        articleRepository.save(article);

        return article.getStatus().equals(status);
    }
}
