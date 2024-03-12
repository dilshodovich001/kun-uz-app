package uz.kun.application.models.rest;

import lombok.Builder;
import uz.kun.domain.models.response.UserResponse;

import java.io.Serializable;
import java.util.List;

@Builder
public record ResponseData<T>(
        String message,
        Integer status,
        T data
) implements Serializable {
    public static <T> ResponseData<T> ok(T data) {
        return new ResponseData<>("success", 200, data);
    }
}