package uz.kun.domain.models.response;

import lombok.Builder;
import uz.kun.domain.constants.UserRole;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record UserResponse(
        Integer id,
        String name,
        String surname,
        String username,
        UserRole role,
        AttachResponse photo,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) implements Serializable {}
