package uz.kun.domain.models.response;

import lombok.Builder;
import lombok.Data;
import uz.kun.domain.constants.UserRole;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private Integer id;
    private String name;
    private String email;
    private String surname;
    private String password;
    private UserRole role;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Integer creatorId;

}
