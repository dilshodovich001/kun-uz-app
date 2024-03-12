package uz.kun.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.kun.infrastructure.config.DetailService;
import uz.kun.domain.exception.ItemAlreadyExistsException;
import uz.kun.domain.exception.ItemNotFoundException;
import uz.kun.domain.entities.UserEntity;
import uz.kun.infrastructure.jpa.repository.UserRepository;
import uz.kun.domain.models.request.UserRequest;
import uz.kun.domain.models.response.UserResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserResponse create(UserRequest request) {

        var userExists = userRepository.findByEmail(request.getEmail()).isPresent();
        if (userExists) throw new ItemAlreadyExistsException("User already exists: "+request.getEmail());

        var userEntity = toEntity(request);
        userRepository.save(userEntity);

        return toResponse(userEntity);
    }

    private UserResponse toResponse(UserEntity e) {
        return UserResponse.builder()
                .id(e.getId())
                .name(e.getName())
                .surname(e.getSurname())
                .email(e.getEmail())
                .role(e.getRole())
                .createdDate(e.getCreatedDate())
                .updatedDate(e.getUpdatedDate())
                .build();
    }

    private UserEntity toEntity(UserRequest request) {
        UserEntity creator = DetailService.getUser();

        return UserEntity.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .creatorId(creator.getId())
                .createdDate(LocalDateTime.now())
                .build();
    }

    public UserResponse getById(int id) {
        var userEntity = get(id);
        return toResponse(userEntity);
    }
    public UserResponse getByEmail(String email) {
        var userEntity = get(email);
        return toResponse(userEntity);
    }

    public UserEntity get(int id){
        return userRepository.findById(id)
                .orElseThrow(()-> new ItemNotFoundException("User not found with id: "+id));
    }

    private UserEntity get(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new ItemNotFoundException("User not found with email: "+email));
    }

    public List<UserResponse> getList(int page, int size) {

        var userEntityList = userRepository.findAll(PageRequest.of(page, size));
        return userEntityList.map(this::toResponse).toList();
    }

    public boolean delete(int id) {
        var userEntity = get(id);
        userRepository.delete(userEntity);

        return true;
    }

    public UserResponse updateByAdmin(UserRequest request, Integer id) {
        var emailExists = userRepository.findByEmail(request.getEmail()).isPresent();
        if (emailExists) throw new ItemAlreadyExistsException("Email already registered, email: "+request.getEmail());

        var userEntity = get(id);

        userEntity.setName(request.getName());
        userEntity.setSurname(request.getSurname());
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userEntity.setRole(request.getRole());
        userEntity.setUpdatedDate(LocalDateTime.now());

        userRepository.save(userEntity);
        return toResponse(userEntity);
    }

    public UserResponse updateByUser(UserRequest request, Integer id) {
        var emailExists = userRepository.findByEmail(request.getEmail()).isPresent();
        if (emailExists) throw new ItemAlreadyExistsException("Email already registered, email: "+request.getEmail());

        var userEntity = get(id);

        userEntity.setName(request.getName());
        userEntity.setSurname(request.getSurname());
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userEntity.setUpdatedDate(LocalDateTime.now());

        userRepository.save(userEntity);
        return toResponse(userEntity);
    }
}
