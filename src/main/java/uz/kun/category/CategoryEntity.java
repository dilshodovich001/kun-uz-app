package uz.kun.category;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.kun.user.UserEntity;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor

public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String nameUz;
    @Column(nullable = false, unique = true)
    private String nameEn;
    @Column(nullable = false, unique = true)
    private String nameRu;
    @Column(nullable = false, unique = true)
    private String key;
    @Column(name = "creator_id", nullable = false)
    private Integer creatorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    private UserEntity user;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
