package uz.kun.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String key;
    @Column(nullable = false, unique = true)
    private String nameUz;
    @Column(nullable = false, unique = true)
    private String nameRu;
    @Column(nullable = false, unique = true)
    private String nameEn;
    @Column
    private Boolean visible = true;
    @Column(name = "creator_id", nullable = false)
    private Integer creatorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}