package uz.kun.domain.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public class BaseEntityID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}