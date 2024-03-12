package uz.kun.domain.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.kun.domain.constants.UserRole;


@Data
public class UserRequest {
    @NotBlank(message = "name not valid")
    private String name;
    @NotBlank(message = "surname not valid")
    private String surname;
    @Email(message = " Wrong email")
    @NotBlank(message = "Email not valid")
    private String email;
    @NotBlank(message = "password not valid")
    private String password;
    @NotNull(message = "Role null")
    private UserRole role;
}
