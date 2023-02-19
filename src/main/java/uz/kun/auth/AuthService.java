package uz.kun.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.kun.config.JwtService;
import uz.kun.exception.ItemAlreadyExistsException;
import uz.kun.exception.ItemNotFoundException;
import uz.kun.user.UserEntity;
import uz.kun.user.UserRepository;
import uz.kun.user.UserRole;

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