package uz.kun.domain.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachResponse {
    private String id;
    private String originalName;
    private String path;
    private Long size;
    private String url;
    private String extension;
    private Long duration;
    private LocalDateTime createdDate;
}
