package uz.kun.infrastructure.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.kun.domain.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
}
