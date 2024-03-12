package uz.kun.domain.models.request;

public record UpdatePhotoRequest(
        Integer userId,
        String photoId
) {}
