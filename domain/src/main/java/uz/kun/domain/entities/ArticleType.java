package uz.kun.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import uz.kun.domain.models.response.ArticleTypeResponse;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "article_types")
public class ArticleType extends BaseEntity {
    private String key;
    private String nameUz;
    private String nameEn;
    private String nameRu;
    private Boolean visible = Boolean.TRUE;

    public ArticleTypeResponse mapToResponse(){
        return ArticleTypeResponse.builder()
                .id(super.getId())
                .key(key)
                .nameEn(nameEn)
                .nameRu(nameRu)
                .nameUz(nameUz)
                .updatedDate(super.getUpdatedDate())
                .createdDate(super.getCreatedDate())
                .build();
    }
}
