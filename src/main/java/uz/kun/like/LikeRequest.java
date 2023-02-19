package uz.kun.like;

import lombok.Data;

@Data
public class LikeRequest {
    private Integer userId;
    private Integer articleId;
    private LikeStatus status;
}
