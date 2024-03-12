package uz.kun.application.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.kun.application.models.rest.ResponseData;
import uz.kun.domain.models.request.UpdatePhotoRequest;
import uz.kun.domain.models.request.UserRequest;
import uz.kun.domain.models.response.UserResponse;
import uz.kun.domain.usecases.UserUseCase;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserUseCase userUseCase;

    @PostMapping
    public void createUser(@RequestBody UserRequest userRequest) {
        userUseCase.createUser(userRequest);
    }

    @GetMapping
    public ResponseData<List<UserResponse>> usersList() {
        return ResponseData.ok(userUseCase.usersList());
    }

    @PutMapping
    public void updateUser(
            @RequestParam("userId") Integer userId,
            @RequestBody UserRequest userRequest
    ){
        userUseCase.updateUser(userId, userRequest);
    }

    @PutMapping("/update-photo")
    public void updatePhoto(@RequestBody UpdatePhotoRequest request){
        userUseCase.updatePhoto(request);
    }

    @DeleteMapping
    public void deleteUser(@RequestParam("userId") Integer userId){
        userUseCase.deleteUser(userId);
    }
}
