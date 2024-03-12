package uz.kun.domain.models.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
}
