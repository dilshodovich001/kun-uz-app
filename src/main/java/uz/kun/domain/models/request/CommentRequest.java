package uz.kun.domain.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRequest {
    @NotBlank(message = "content not valid")
    private String content;
    private Integer userId;
    private Integer articleId;
}
