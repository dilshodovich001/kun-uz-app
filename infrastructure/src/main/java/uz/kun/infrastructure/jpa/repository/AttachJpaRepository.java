package uz.kun.infrastructure.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kun.domain.entities.Attach;
import uz.kun.domain.entities.User;

import java.util.Optional;

@Repository
public interface AttachJpaRepository extends JpaRepository<Attach, String> {
    Optional<Attach> findByFilename(String filename);
}
