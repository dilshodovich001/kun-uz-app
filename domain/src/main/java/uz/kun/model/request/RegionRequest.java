package uz.kun.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegionRequest {
    @NotBlank(message = "key not valid")
    private String key;
    @NotBlank(message = "name uz not valid")
    private String nameUz;
    @NotBlank(message = "name ru not valid")
    private String nameRu;
    @NotBlank(message = "name en not valid")
    private String nameEn;
}
