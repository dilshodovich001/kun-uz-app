package uz.kun.config;

import org.springframework.security.core.context.SecurityContextHolder;
import uz.kun.user.UserEntity;

public record DetailService() {
    public static UserEntity getUser(){
        return (UserEntity) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
