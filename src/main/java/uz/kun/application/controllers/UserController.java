package uz.kun.application.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.domain.models.request.UserRequest;
import uz.kun.domain.models.response.UserResponse;
import uz.kun.application.services.UserService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> create(
            @RequestBody UserRequest request
    ) {
        log.info("User create: {}", request.toString());
        return ResponseEntity.ok(userService.create(request));
    }

    @GetMapping
    public ResponseEntity<UserResponse> getById(
            @RequestParam int id
    ) {
        log.info("Get user: {}", id);
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/email")
    public ResponseEntity<UserResponse> getByEmail(
            @RequestParam("email") String email
    ) {
        log.info("Get user: {}", email);
        return ResponseEntity.ok(userService.getByEmail(email));
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponse>> getList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ){
        log.info("Getting user list page: {} size: {}", page, size);
        return ResponseEntity.ok(userService.getList(page,size));
    }

    @PutMapping("/update-by-admin")
    public ResponseEntity<UserResponse> updateByAdmin(
            @RequestBody UserRequest request,
            @RequestParam("id") Integer id
    ){
        log.info("Update request with admin, request: {}", request.toString());
        return ResponseEntity.ok(userService.updateByAdmin(request, id));
    }

    @PutMapping("/update-by-user")
    public ResponseEntity<UserResponse> updateByUser(
            @RequestBody UserRequest request,
            @RequestParam("id") Integer id
    ){
        log.info("Update request with user, request: {}", request.toString());
        return ResponseEntity.ok(userService.updateByUser(request, id));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam("id") int id){
        log.info("Deleting user: {}",id);
        boolean result = userService.delete(id);

        if (result) log.info("User deleted with id: {}, result: {}",id, true);
        return ResponseEntity.ok("User successfully deleted !");
    }

}
