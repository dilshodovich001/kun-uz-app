package uz.kun.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RegionResponse {
    private Integer id;
    private String key;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String name;
    private Integer creatorId;
    private LocalDateTime createdDate;
}
