package uz.kun.domain.models.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Builder
@Data
public class ArticleTypeResponse {
    private Integer id;
    private String name;
    private String key;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private Integer creatorId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
