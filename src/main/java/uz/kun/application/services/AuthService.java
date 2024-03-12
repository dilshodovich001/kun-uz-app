package uz.kun.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.kun.domain.models.request.AuthRequest;
import uz.kun.domain.models.response.AuthResponse;
import uz.kun.domain.models.request.RegisterRequest;
import uz.kun.infrastructure.config.JwtService;
import uz.kun.domain.exception.ItemAlreadyExistsException;
import uz.kun.domain.exception.ItemNotFoundException;
import uz.kun.domain.entities.UserEntity;
import uz.kun.infrastructure.jpa.repository.UserRepository;
import uz.kun.domain.constants.UserRole;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public String register(RegisterRequest request) {
        boolean userExists = userRepository.findByEmail(request.getEmail()).isPresent();
        if (userExists) throw new ItemAlreadyExistsException("User already exists, with email "+ request.getEmail());

        var user = UserEntity.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdDate(LocalDateTime.now())
                .role(UserRole.USER)
                .build();

        userRepository.save(user);
        return "User successfully registered !";
    }

    public AuthResponse login(AuthRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ItemNotFoundException("Not found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );


        var token = jwtService.generateToken(user);

        /* TOKEN eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJpc21vaWxAZ21haWwuY29tIiwiaWF0IjoxNjc2NjU5MDQ5LCJleHAiOjE2NzY2NjA0ODl9.7zBp8OHfocfvZ3N3XkypS9al8YmsvYelXUzipEStj1Q */

        return AuthResponse.builder()
                .token(token)
                .build();
    }
}