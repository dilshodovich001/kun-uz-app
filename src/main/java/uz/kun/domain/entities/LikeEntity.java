package uz.kun.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.kun.domain.constants.LikeStatus;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "likes")
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LikeStatus status;

    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    @Column(name = "article_id", nullable = false)
    private Integer articleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
