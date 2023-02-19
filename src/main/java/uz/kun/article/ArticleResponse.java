package uz.kun.article;

import lombok.Builder;
import lombok.Data;
import uz.kun.article.type.ArticleTypeResponse;
import uz.kun.attach.AttachDto;
import uz.kun.category.CategoryResponse;
import uz.kun.region.RegionResponse;
import uz.kun.tag.TagResponse;
import uz.kun.user.UserResponse;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ArticleResponse {
    private Integer id;
    private String title;
    private String description;
    private String content;
    private Boolean visible;
    private UserResponse creator;
    private AttachDto attach;
    private RegionResponse region;
    private ArticleTypeResponse articleType;
    private CategoryResponse category;
    private Integer viewCount;
    private Integer sharedCount;
    private ArticleStatus status;
    private List<TagResponse> tagList;
    private LocalDateTime publishedDate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
