package uz.kun.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.kun.domain.exception.ItemNotFoundException;
import uz.kun.domain.entities.LikeEntity;
import uz.kun.infrastructure.jpa.repository.LikeRepository;
import uz.kun.domain.models.request.LikeRequest;
import uz.kun.domain.models.response.LikeResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final ArticleService articleService;

    public LikeResponse create(LikeRequest request, Integer userId) {
        var user = userService.get(userId);
        var article = articleService.get(request.getArticleId());

        Optional<LikeEntity> optionalLike = likeRepository.findByArticleIdAndUserId(article.getId(), userId);
        if (optionalLike.isPresent()) {
            var likeEntity = optionalLike.get();
            likeEntity.setStatus(request.getStatus());
            likeEntity.setUpdatedDate(LocalDateTime.now());

            likeRepository.save(likeEntity);
            return toResponse(likeEntity);
        }

        var likeEntity = LikeEntity.builder()
                .userId(user.getId())
                .user(user)
                .articleId(article.getId())
                .article(article)
                .status(request.getStatus())
                .createdDate(LocalDateTime.now())
                .build();

        likeRepository.save(likeEntity);
        return toResponse(likeEntity);
    }

    private LikeResponse toResponse(LikeEntity e) {
        return LikeResponse.builder()
                .id(e.getId())
                .articleId(e.getArticleId())
                .userId(e.getUserId())
                .status(e.getStatus())
                .updatedDate(e.getUpdatedDate())
                .createdDate(e.getCreatedDate())
                .build();
    }

    private LikeEntity get(Integer id) {
        return likeRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Not found"));
    }

    public boolean delete(Integer id) {
        var like = get(id);
        likeRepository.delete(like);
        return true;
    }

    public List<LikeResponse> getAllByArticleId(int articleId, int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        var article = articleService.get(articleId);

        return likeRepository.findAllByArticle(article, pageable)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<LikeResponse> getAllByUserId(Integer userId) {
        var user = userService.get(userId);
        return likeRepository.findAllByUser(user)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<LikeResponse> getList(int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC));
        return likeRepository.findAll(pageable)
                .stream()
                .map(this::toResponse)
                .toList();
    }
}
