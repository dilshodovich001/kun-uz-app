package uz.kun.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "tag")
@AllArgsConstructor
@NoArgsConstructor
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String key;
    @Column(name = "name_uz", nullable = false, unique = true)
    private String nameUz;
    @Column(name = "name_ru", nullable = false, unique = true)
    private String nameRu;
    @Column(name = "name_en", nullable = false, unique = true)
    private String nameEn;
    @Column
    private Boolean visible = true;
    @Column(name = "creator_id", nullable = false)
    private Integer creatorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
