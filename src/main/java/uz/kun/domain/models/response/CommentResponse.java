package uz.kun.domain.models.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Integer id;
    private String content;
    private Integer userId;
    private Integer articleId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
