package uz.kun.infrastructure.jpa.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kun.domain.entities.ArticleEntity;
import uz.kun.domain.entities.LikeEntity;
import uz.kun.domain.entities.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {
    Optional<LikeEntity> findByArticleIdAndUserId(Integer articleId, Integer userId);
    List<LikeEntity> findAllByArticle(ArticleEntity article, PageRequest pageRequest);
    List<LikeEntity> findAllByUser(UserEntity user);
}
