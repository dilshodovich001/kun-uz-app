package uz.kun.infrastructure.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.kun.domain.entities.Region;

import java.util.Optional;

public interface RegionJpaRepository extends JpaRepository<Region, Integer> {
    Optional<Region> findByKey(String key);
}
