package uz.kun.infrastructure.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kun.domain.entities.AttachEntity;

@Repository
public interface AttachRepository extends JpaRepository<AttachEntity, String> {
}
