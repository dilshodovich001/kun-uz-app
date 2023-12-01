package uz.kun.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.kun.model.request.UserRequest;
import uz.kun.model.response.UserResponse;
import uz.kun.service.user.UserService;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // create user
    @GetMapping("/create")
    public String createUser(Model model) {
        model.addAttribute(
                "userRequest",
                UserRequest.builder().build()
        );
        return "user/create-user";
    }

    @PostMapping("/create")
    public String createUser(
            @Valid
            @ModelAttribute("userRequest")
            UserRequest userRequest,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "user/create-user";
        }

        userService.createUser(userRequest);
        return "redirect:/user/list";
    }

    //  Read all users
    @GetMapping("/list")
    public String getAllUsersList(Model model) {
        var users = userService.getAllUsers();
        model.addAttribute("users", users);

        return "user/users-list";
    }

    //  Update user page
    @GetMapping("/update/{userId}")
    public String editUser(
            @PathVariable Integer userId,
            Model model
    ) {

        var user = userService.getUser(userId);
        var updateUserRequest = UserRequest.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();

        model.addAttribute("updateUserRequest", updateUserRequest);
        model.addAttribute("userId", userId);

        return "user/update-user";
    }

    // update user
    @PostMapping("/update/{userId}")
    public String editUser(
            @PathVariable Integer userId,
            @ModelAttribute("editUserRequest")
            UserRequest userRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "user/update-user";
        }

        userService.updateUser(userId, userRequest);
        return "redirect:/user/list";
    }

    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return "redirect:/user/list";
    }
}
