package uz.kun.domain.models.response;

import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record ArticleTypeResponse(
        Integer id,
        String key,
        String nameUz,
        String nameEn,
        String nameRu,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) implements Serializable { }
