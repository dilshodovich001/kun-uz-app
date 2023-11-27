package uz.kun.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String role;
}
