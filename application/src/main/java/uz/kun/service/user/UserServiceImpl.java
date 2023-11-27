package uz.kun.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import uz.kun.entity.User;
import uz.kun.model.request.UserRequest;
import uz.kun.model.response.UserResponse;
import uz.kun.repository.UserRepository;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        System.out.println("userRequest.toString() = " + userRequest.toString());

        User userEntity = User.builder()
                .firstname(userRequest.getFirstname())
                .lastname(userRequest.getLastname())
                .email(userRequest.getEmail())
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .role("USER")
                .build();

        userRepository.save(userEntity);

        Function<User, UserResponse> responseMapper = mapToResponse();
        return responseMapper.apply(userEntity);
    }

    private Function<User, UserResponse> mapToResponse() {
        return userEntity -> UserResponse.builder()
                .id(userEntity.getId())
                .firstname(userEntity.getFirstname())
                .lastname(userEntity.getLastname())
                .email(userEntity.getEmail())
                .username(userEntity.getUsername())
                .role(userEntity.getRole())
                .build();
    }

    @Override
    public UserResponse getUser(Integer userId) {
        return userRepository
                .findById(userId)
                .map(mapToResponse())
                .orElseThrow();
    }

    @Override
    public List<UserResponse> getAllUsers() {

        try {

            Thread.sleep(Duration.ofSeconds(3).toMillis());

            return userRepository
                    .findAll()
                    .stream()
                    .map(mapToResponse())
                    .toList();

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }


    @Override
    public UserResponse updateUser(Integer userId, UserRequest userRequest) {

        User userEntity = userRepository
                .findById(userId)
                .orElseThrow();

        userEntity.setFirstname(userRequest.getFirstname());
        userEntity.setLastname(userRequest.getLastname());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setUsername(userRequest.getUsername());
        userEntity.setPassword(userRequest.getPassword());

        userRepository.save(userEntity);

        Function<User, UserResponse> responseMapper = mapToResponse();
        return responseMapper.apply(userEntity);
    }


    @Override
    public void deleteUser(Integer userId) {
        User userEntity = userRepository
                .findById(userId)
                .orElseThrow();

        userRepository.delete(userEntity);
    }
}
