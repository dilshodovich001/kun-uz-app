package uz.kun.infrastructure.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kun.domain.entities.TagEntity;

import java.util.Optional;
@Repository
public interface TagRepository extends JpaRepository<TagEntity, Integer> {
    Optional<TagEntity> findByKey(String key);
}
