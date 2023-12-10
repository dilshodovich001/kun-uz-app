package uz.kun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kun.entity.Region;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region,Integer> {
    Optional<Region> findByKey(String key);
}