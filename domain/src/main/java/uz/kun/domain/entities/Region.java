package uz.kun.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import uz.kun.domain.models.response.RegionResponse;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "regions")
public class Region extends BaseEntityID {
    private String key;
    private String nameUz;
    private String nameEn;
    private String nameRu;

    public RegionResponse mapToResponse() {
        return RegionResponse.builder()
                .id(super.getId())
                .key(key)
                .nameUz(nameUz)
                .nameEn(nameEn)
                .nameRu(nameRu)
                .build();
    }
}