package uz.kun.domain.models.request;

import lombok.Data;
import uz.kun.domain.constants.LikeStatus;

@Data
public class LikeRequest {
    private Integer userId;
    private Integer articleId;
    private LikeStatus status;
}
