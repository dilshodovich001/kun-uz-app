package uz.kun.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryRequest {
    @NotBlank(message = "kategoriya nomi yaroqsiz qayta urinib koring")
    private String nameUz;

    @NotBlank(message = "category name ru not valid")
    private String nameRu;

    @NotBlank(message = "category name en not valid")
    private String nameEn;

    @NotBlank(message = "key not valid")
    private String key;
}