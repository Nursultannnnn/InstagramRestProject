package peaksoft.instagramrestproject.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.instagramrestproject.dto.SimpleResponse;
import peaksoft.instagramrestproject.dto.user.UserResponse; // Импортируем твой новый UserResponse
import peaksoft.instagramrestproject.service.FollowerService;

import java.util.List;

@RestController
@RequestMapping("/api/followers")
@RequiredArgsConstructor
public class FollowerApi {
    private final FollowerService followerService;

    // Подписаться / Отписаться
    @PostMapping("/follow/{targetUserId}")
    public SimpleResponse follow(@PathVariable Long targetUserId) {
        return followerService.followUser(targetUserId);
    }

    // Получить список всех подписчиков конкретного пользователя
    @GetMapping("/subscribers/{userId}")
    public List<UserResponse> getAllSubscribers(@PathVariable Long userId) {
        return followerService.getAllSubscribers(userId);
    }

    // Получить список всех, на кого подписан конкретный пользователь
    @GetMapping("/subscriptions/{userId}")
    public List<UserResponse> getAllSubscriptions(@PathVariable Long userId) {
        return followerService.getAllSubscriptions(userId);
    }
}