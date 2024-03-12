package uz.kun.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.kun.domain.constants.ArticleStatus;
import uz.kun.region.RegionEntity;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "article")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String title;
    @Column(nullable = false, columnDefinition = "text")
    private String description;
    @Column(nullable = false, columnDefinition = "text")
    private String content;
    @Column
    private Boolean visible = true;
    @Column(name = "creator_id", nullable = false)
    private Integer creatorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;
    @Column(name = "attach_id", nullable = false)
    private String attachId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity attach;
    @Column(name = "region_id", nullable = false)
    private Integer regionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private RegionEntity region;

    @Column(name = "type_id", nullable = false)
    private Integer typeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", insertable = false, updatable = false)
    private ArticleTypeEntity type;
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;
    @Column(name = "view_count")
    private Integer viewCount = 0;
    @Column(name = "shared_count")
    private Integer sharedCount = 0;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ArticleStatus status;
    @Column(name = "tag_list", columnDefinition = "integer[]")
    @ElementCollection
    private List<Integer> tagList;
    @Column(name = "published_date")
    private LocalDateTime publishedDate;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
}
