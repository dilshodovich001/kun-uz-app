package uz.kun.infrastructure.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kun.domain.entities.ArticleType;

import java.util.Optional;

@Repository
public interface ArticleTypeJpaRepository extends JpaRepository<ArticleType, Integer> {
    Optional<ArticleType> findByKey(String key);
}
