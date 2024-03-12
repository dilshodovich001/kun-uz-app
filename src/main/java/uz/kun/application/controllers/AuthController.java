package uz.kun.application.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.kun.domain.models.request.AuthRequest;
import uz.kun.domain.models.response.AuthResponse;
import uz.kun.application.services.AuthService;
import uz.kun.domain.models.request.RegisterRequest;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request){
        log.info("Register request: {}", request.toString());

        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){
        log.info("Login request: {}", request.toString());
        return ResponseEntity.ok(authService.login(request));
    }
}
