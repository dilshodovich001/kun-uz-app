package uz.kun.like;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LikeResponse {
    private Integer id;
    private Integer userId;
    private Integer articleId;
    private LikeStatus status;
    private LocalDateTime updatedDate;
    private LocalDateTime createdDate;
}
