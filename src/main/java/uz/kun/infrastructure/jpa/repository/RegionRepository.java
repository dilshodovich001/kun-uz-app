package uz.kun.infrastructure.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<RegionEntity, Integer> {
    Optional<RegionEntity> findByKey(String key);
}
