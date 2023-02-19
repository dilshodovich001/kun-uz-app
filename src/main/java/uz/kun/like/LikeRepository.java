package uz.kun.like;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kun.article.ArticleEntity;
import uz.kun.user.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {
    Optional<LikeEntity> findByArticleIdAndUserId(Integer articleId, Integer userId);
    List<LikeEntity> findAllByArticle(ArticleEntity article, PageRequest pageRequest);
    List<LikeEntity> findAllByUser(UserEntity user);
}
