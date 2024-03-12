package uz.kun.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import uz.kun.domain.models.response.AttachResponse;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "attaches")
public class Attach extends BaseEntityUUID {
    private String filename;
    private String path;
    private String url;
    private Long size;
    public AttachResponse mapToResponse(){
        return AttachResponse.builder()
                .uuid(super.getUuid())
                .filename(filename)
                .path(path)
                .url(url)
                .size(size)
                .build();
    }
}