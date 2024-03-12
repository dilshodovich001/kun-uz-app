package uz.kun.infrastructure.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kun.domain.entities.ArticleTypeEntity;

import java.util.Optional;
@Repository
public interface ArticleTypeRepository extends JpaRepository<ArticleTypeEntity, Integer> {
    Optional<ArticleTypeEntity> findByKey(String key);
}
