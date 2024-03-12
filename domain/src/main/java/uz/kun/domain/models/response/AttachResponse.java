package uz.kun.domain.models.response;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record AttachResponse(
        String uuid,
        String filename,
        String path,
        String url,
        Long size
) implements Serializable { }