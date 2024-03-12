package uz.kun.domain.models.request;

import uz.kun.domain.constants.UserRole;
import uz.kun.domain.entities.User;

public record UserRequest(
        String name,
        String surname,
        String username,
        String password
) {
    public User mapToUserEntity(){
        return User.builder()
                .name(this.name)
                .surname(this.surname)
                .username(this.username)
                .password(this.password)
                .role(UserRole.USER)
                .build();
    }
}