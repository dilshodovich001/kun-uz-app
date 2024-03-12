package uz.kun.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import uz.kun.domain.constants.UserRole;
import uz.kun.domain.models.response.UserResponse;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String name;
    private String surname;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String photoUUId;
    @ManyToOne
    @JoinColumn(name = "photo_uuid", updatable = false, insertable = false)
    private Attach photo;

    public UserResponse mapToResponse()
    {
        return UserResponse.builder()
                .id(super.getId())
                .name(name)
                .surname(surname)
                .username(username)
                .role(role)
                .photo(photo.mapToResponse())
                .createdDate(super.getCreatedDate())
                .updatedDate(super.getUpdatedDate())
                .build();
    }
}
