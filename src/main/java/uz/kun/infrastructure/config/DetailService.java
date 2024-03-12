package uz.kun.infrastructure.config;

import org.springframework.security.core.context.SecurityContextHolder;
import uz.kun.domain.entities.UserEntity;

public record DetailService() {
    public static UserEntity getUser(){
        return (UserEntity) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
