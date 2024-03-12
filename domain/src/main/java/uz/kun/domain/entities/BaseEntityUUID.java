package uz.kun.domain.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public class BaseEntityUUID {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;
}