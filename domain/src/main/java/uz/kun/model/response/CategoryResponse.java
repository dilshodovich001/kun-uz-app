package uz.kun.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CategoryResponse {
    private Integer id;
    private String name;
    private String nameUz;
    private String nameEn;
    private String nameRu;
    private String key;
    private Integer creatorId;
    private LocalDateTime createdDate;
}