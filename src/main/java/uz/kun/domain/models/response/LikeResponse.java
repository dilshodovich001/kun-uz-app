package uz.kun.domain.models.response;

import lombok.Builder;
import lombok.Data;
import uz.kun.domain.constants.LikeStatus;

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
