package uz.kun.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {
    @NotBlank(message = "name uz not valid")
    private String nameUz;
    @NotBlank(message = "name ru not valid")
    private String nameRu;
    @NotBlank(message = "name en not valid")
    private String nameEn;
    @NotBlank(message = "key not valid")
    private String key;
//    private String name;
    private Integer userId;
}
