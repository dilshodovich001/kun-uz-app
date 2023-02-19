package uz.kun.article.type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ArticleTypeRepository extends JpaRepository<ArticleTypeEntity, Integer> {
    Optional<ArticleTypeEntity> findByKey(String key);
}
