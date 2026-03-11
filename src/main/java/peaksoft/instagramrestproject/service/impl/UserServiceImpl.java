package peaksoft.instagramrestproject.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.instagramrestproject.config.jwt.JwtService;
import peaksoft.instagramrestproject.dto.*;
import peaksoft.instagramrestproject.dto.post.PostResponse;
import peaksoft.instagramrestproject.dto.user.UserResponse;
import peaksoft.instagramrestproject.entity.Follower;
import peaksoft.instagramrestproject.entity.User;
import peaksoft.instagramrestproject.entity.UserInfo;
import peaksoft.instagramrestproject.enums.Role;
import peaksoft.instagramrestproject.repo.PostRepo;
import peaksoft.instagramrestproject.repo.UserRepo;
import peaksoft.instagramrestproject.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional

public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final PostRepo postRepo;

    @Override
    public AuthResponse signUp(SignUpRequest request) {
        if (userRepo.existsByEmail(request.email())) {
            throw new RuntimeException("Email " + request.email() + " уже занят!");
        }
        if (userRepo.existsByUsername(request.username())) {
            throw new RuntimeException("Username " + request.username() + " уже занят!");
        }

        if (!request.phoneNumber().startsWith("+996")) {
            throw new RuntimeException("Номер телефона должен начинаться с +996!");
        }

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .phoneNumber(request.phoneNumber())
                .role(Role.USER) // По умолчанию все юзеры
                .build();

        Follower follower = new Follower();
        follower.setUser(user);
        follower.setSubscribers(new ArrayList<>());
        follower.setSubscriptions(new ArrayList<>());
        user.setFollower(follower);

        UserInfo userInfo = UserInfo.builder()
                .fullName(request.fullName())
                .user(user)
                .build();
        user.setUserInfo(userInfo);

        userRepo.save(user);

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .build();
    }

    @Override
    public AuthResponse signIn(SignInRequest request) {
        User user = userRepo.getUserByEmail(request.email()).orElseThrow(() ->
                new NoSuchElementException("Пользователь не найден"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Неверный пароль!");
        }

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .build();
    }

    @Override
    public SimpleResponse updateProfile(Long userId, SignUpRequest request) {
        User userToUpdate = userRepo.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Пользователь с id " + userId + " не найден"));

        String currentUserEmail = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getName();

        if (!userToUpdate.getEmail().equals(currentUserEmail)) {
            throw new RuntimeException("Вы не можете редактировать чужой профиль!");
        }

        userToUpdate.setUsername(request.username());
        userToUpdate.setEmail(request.email());
        if (request.password() != null && !request.password().isBlank()) {
            userToUpdate.setPassword(passwordEncoder.encode(request.password()));
        }

        if (userToUpdate.getUserInfo() != null) {
            userToUpdate.getUserInfo().setFullName(request.fullName());
        }

        userRepo.save(userToUpdate);

        return SimpleResponse.builder()
                .message("Профиль успешно обновлен")
                .status(org.springframework.http.HttpStatus.OK)
                .build();
    }

    @Override
    public SimpleResponse deleteUser(Long userId) {
        User userToDelete = userRepo.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        String currentUserEmail = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getName();

        if (!userToDelete.getEmail().equals(currentUserEmail)) {
            throw new RuntimeException("Вы не можете удалить чужой аккаунт!");
        }

        userRepo.delete(userToDelete);
        return SimpleResponse.builder()
                .message("Аккаунт успешно удален")
                .status(org.springframework.http.HttpStatus.OK)
                .build();
    }


    @Override
    public UserProfileResponse userProfile(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));

        List<PostResponse> userPosts = postRepo.findAllByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(post -> new PostResponse(
                        post.getId(),
                        post.getImages().isEmpty() ? null : post.getImages().get(0).getImageURL(),
                        post.getDescription(),
                        post.getLikes().size()
                ))
                .toList();

        return UserProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getUserInfo().getFullName())
                .biography(user.getUserInfo().getBiography())
                .image(user.getUserInfo().getImage())
                .countSubscribers(user.getFollower().getSubscribers().size())
                .countSubscriptions(user.getFollower().getSubscriptions().size())
                .posts(userPosts)
                .build();
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepo.findAll().stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getUserInfo() != null ? user.getUserInfo().getImage() : null,
                        user.getFollower() != null ? user.getFollower().getSubscribers().size() : 0,
                        user.getFollower() != null ? user.getFollower().getSubscriptions().size() : 0,
                        user.getUserInfo() != null ? user.getUserInfo().getFullName() : null
                ))
                .toList();
    }

    @Override
    public UserProfileResponse getUserById(Long userId) {
        return userProfile(userId);
    }

    @Override
    public List<UserResponse> search(String keyword) {
        return userRepo.searchUsers(keyword).stream()
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getUsername(),
                        u.getUserInfo() != null ? u.getUserInfo().getImage() : null,
                        u.getFollower() != null ? u.getFollower().getSubscribers().size() : 0,
                        u.getFollower() != null ? u.getFollower().getSubscriptions().size() : 0,
                        u.getUserInfo() != null ? u.getUserInfo().getFullName() : null
                ))
                .toList();
    }
}
