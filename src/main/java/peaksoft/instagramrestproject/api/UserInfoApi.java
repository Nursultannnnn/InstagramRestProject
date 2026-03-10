package peaksoft.instagramrestproject.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.instagramrestproject.dto.SimpleResponse;
import peaksoft.instagramrestproject.dto.userinfo.UserInfoRequest;
import peaksoft.instagramrestproject.dto.userinfo.UserInfoResponse;
import peaksoft.instagramrestproject.service.UserInfoService;

@RestController
@RequestMapping("/api/userInfo") // Твой путь
@RequiredArgsConstructor
public class UserInfoApi {

    private final UserInfoService userInfoService;

    // Создать/привязать инфо к юзеру
    @PostMapping("/{userId}")
    public SimpleResponse save(@PathVariable Long userId, @RequestBody @Valid UserInfoRequest userInfoRequest) {
        return userInfoService.saveUserInfo(userId, userInfoRequest);
    }

    // Получить инфо по ID юзера
    @GetMapping("/by-user/{userId}")
    public UserInfoResponse getUserInfo(@PathVariable Long userId) {
        return userInfoService.findUserInfoByUserId(userId);
    }

    // Обновить по ID юзера
    @PutMapping("/{userId}")
    public SimpleResponse update(@PathVariable Long userId, @RequestBody UserInfoRequest request) {
        return userInfoService.update(userId, request);
    }

    // Удалить конкретную запись инфо по её ID
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return userInfoService.delete(id);
    }

    @PatchMapping("/image/{userId}")
    public SimpleResponse changeImage(@PathVariable Long userId, @RequestParam String newImage) {
        return userInfoService.changeImage(userId, newImage);
    }

    @DeleteMapping("/image/{userId}")
    public SimpleResponse deleteImage(@PathVariable Long userId) {
        return userInfoService.deleteImage(userId);
    }
}