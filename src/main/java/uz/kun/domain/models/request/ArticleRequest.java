package uz.kun.domain.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ArticleRequest {
    @NotBlank(message = "title not valid")
    private String title;
    @NotBlank(message = "description not valid")
    private String description;
    @NotBlank(message = "content not valid")
    private String content;
    private Integer creatorId;
    private String attachId;
    private Integer regionId;
    private Integer typeId;
    private Integer categoryId;
    private List<Integer> tagList;
}
