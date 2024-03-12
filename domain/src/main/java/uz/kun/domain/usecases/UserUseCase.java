package uz.kun.domain.usecases;

import uz.kun.domain.models.request.UpdatePhotoRequest;
import uz.kun.domain.models.request.UserRequest;
import uz.kun.domain.models.response.UserResponse;

import java.util.List;

public interface UserUseCase {
    void createUser(UserRequest userRequest);
    void updateUser(Integer userId, UserRequest userRequest);
    void deleteUser(Integer userId);
    void updatePhoto(UpdatePhotoRequest userPhotoRequest);
    List<UserResponse> usersList();
}
