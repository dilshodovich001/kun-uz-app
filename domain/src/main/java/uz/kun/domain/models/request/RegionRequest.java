package uz.kun.domain.models.request;

import uz.kun.domain.entities.Region;

public record RegionRequest(
        String key,
        String nameUz,
        String nameEn,
        String nameRu
) {
    public Region mapToEntity() {
        return Region.builder()
                .key(key)
                .nameUz(nameUz)
                .nameEn(nameEn)
                .nameRu(nameRu)
                .build();
    }
}