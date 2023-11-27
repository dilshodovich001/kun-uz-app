package uz.kun.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.kun.model.request.UserRequest;
import uz.kun.model.response.UserResponse;
import uz.kun.service.user.UserService;

import java.util.List;

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
        List<UserResponse> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user/users-list";
    }

    //  Update user page
    @GetMapping("/edit/{userId}")
    public String editUser(
            @PathVariable Integer userId,
            Model model
    ) {

        UserResponse userResponse = userService.getUser(userId);

        UserRequest editUserRequest = UserRequest.builder()
                .firstname(userResponse.getFirstname())
                .lastname(userResponse.getLastname())
                .email(userResponse.getEmail())
                .username(userResponse.getUsername())
                .build();

        model.addAttribute("editUserRequest", editUserRequest);
        model.addAttribute("userId", userId);

        return "user/edit-user";
    }

    // update user
    @PostMapping("/edit/{userId}")
    public String editUser(
            @PathVariable Integer userId,
            @ModelAttribute("editUserRequest")
            UserRequest userRequest
    ) {
        userService.updateUser(userId, userRequest);
        return "redirect:/user/list";
    }

    //  delete user
    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return "redirect:/user/list";
    }
}
