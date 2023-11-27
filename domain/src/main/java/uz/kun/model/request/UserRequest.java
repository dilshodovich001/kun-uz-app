package uz.kun.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {

    @NotBlank(message="Firstname is empty")
    private String firstname;

    @NotBlank(message="Lastname is empty")
    private String lastname;

    @NotBlank(message="Email is empty")
    @Email(
            regexp = "^[A-Za-z0-9+_.-]+@(.+)$",
            message = "Email not valid"
    )
    private String email;

    @NotBlank(message="Username is empty")
    private String username;

    @NotBlank(message="Password is empty")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$",
            message = "password not valid"
    )
    private String password;
}
