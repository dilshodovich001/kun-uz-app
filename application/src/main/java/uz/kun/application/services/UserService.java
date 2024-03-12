package uz.kun.application.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.kun.application.models.exception.APIException;
import uz.kun.domain.entities.User;
import uz.kun.domain.models.request.UpdatePhotoRequest;
import uz.kun.domain.models.request.UserRequest;
import uz.kun.domain.models.response.UserResponse;
import uz.kun.domain.usecases.AttachUseCase;
import uz.kun.domain.usecases.UserUseCase;
import uz.kun.infrastructure.jpa.repository.UserJpaRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserUseCase {

    private final UserJpaRepository userJpaRepository;
    private final AttachUseCase attachUseCase;

    @Override
    public void createUser(UserRequest userRequest)
    {
        var optionalUser = userJpaRepository.findByUsername(userRequest.username());
        if (optionalUser.isPresent()) throw new APIException("User exist", 400);

        var userEntity = userRequest.mapToUserEntity();
        userJpaRepository.save(userEntity);
    }

    @Override
    public void updateUser(
            Integer userId,
            UserRequest userRequest
    ) {
        var userEntity = userJpaRepository
                .findById(userId)
                .orElseThrow(() -> new APIException("User not found...", 404));

        userEntity.setName(userRequest.name());
        userEntity.setSurname(userRequest.surname());
        userEntity.setUsername(userRequest.username());

        userJpaRepository.save(userEntity);
    }

    @Override
    public void deleteUser(Integer userId) {
        userJpaRepository.deleteById(userId);
    }

    @Override
    public void updatePhoto(UpdatePhotoRequest request) {
        var user = userJpaRepository
                .findById(request.userId())
                .orElseThrow(() -> new APIException("User not found...", 404));

        user.setPhotoUUId(user.getPhotoUUId());
        userJpaRepository.save(user);
    }

    @Override
    public List<UserResponse> usersList() {
        return userJpaRepository
                .findAll()
                .stream()
                .map(User::mapToResponse)
                .toList();
    }
}