package uz.kun.service.user;

import uz.kun.model.request.UserRequest;
import uz.kun.model.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    UserResponse getUser(Integer userId);
    List<UserResponse> getAllUsers();
    UserResponse updateUser(Integer userId, UserRequest userRequest);
    void deleteUser(Integer userId);
}