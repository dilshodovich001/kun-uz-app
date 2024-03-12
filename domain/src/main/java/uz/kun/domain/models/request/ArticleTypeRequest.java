package uz.kun.domain.models.request;

import uz.kun.domain.entities.ArticleType;

public record ArticleTypeRequest(
        String key,
        String nameUz,
        String nameEn,
        String nameRu
) {
    public ArticleType mapToEntity() {
        return ArticleType.builder()
                .key(key)
                .nameEn(nameEn)
                .nameRu(nameRu)
                .nameUz(nameUz)
                .build();
    }
}