package uz.kun.domain.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ArticleTypeRequest {
    @NotBlank(message = "key not valid")
    private String key;
    @NotBlank(message = "name uz not valid")
    private String nameUz;
    @NotBlank(message = "name ru not valid")
    private String nameRu;
    @NotBlank(message = "name en not valid")
    private String nameEn;
    private Integer userId;
}
