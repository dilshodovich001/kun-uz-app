package uz.kun.domain.models.response;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record RegionResponse(
        Integer id,
        String key,
        String nameUz,
        String nameEn,
        String nameRu
) implements Serializable { }