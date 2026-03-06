package peaksoft.instagramrestproject.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.instagramrestproject.dto.SignUpRequest;
import peaksoft.instagramrestproject.dto.SimpleResponse;
import peaksoft.instagramrestproject.dto.UserProfileResponse;
import peaksoft.instagramrestproject.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    // Получить всех пользователей
    @GetMapping
    public List<UserProfileResponse> getAll() {
        return userService.getAllUsers();
    }

    // Получить профиль конкретного юзера (Твое ТЗ)
    @GetMapping("/{userId}")
    public UserProfileResponse getProfile(@PathVariable Long userId) {
        return userService.userProfile(userId);
    }

    @PutMapping("/{userId}")
    public SimpleResponse update(@PathVariable Long userId, @RequestBody SignUpRequest request) {
        return userService.updateProfile(userId, request);
    }

    @DeleteMapping("/{userId}")
    public SimpleResponse delete(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

//    @GetMapping("/{userId}")
//    public UserProfileResponse getById(@PathVariable Long userId) {
//        return userService.getUserById(userId); // Как только ты напишешь эту строку, метод в сервисе оживет!
//    }

}