package peaksoft.instagramrestproject.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.instagramrestproject.dto.SimpleResponse;
import peaksoft.instagramrestproject.dto.userinfo.UserInfoRequest;
import peaksoft.instagramrestproject.dto.userinfo.UserInfoResponse;
import peaksoft.instagramrestproject.entity.User;
import peaksoft.instagramrestproject.entity.UserInfo;
import peaksoft.instagramrestproject.enums.Gender;
import peaksoft.instagramrestproject.repo.UserInfoRepo;
import peaksoft.instagramrestproject.repo.UserRepo;
import peaksoft.instagramrestproject.service.UserInfoService;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserRepo userRepo;
    private final UserInfoRepo userInfoRepo;

    @Override
    @Transactional
    public SimpleResponse saveUserInfo(Long userId, UserInfoRequest request) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        UserInfo userInfo = user.getUserInfo();
        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setUser(user);
        }

        userInfo.setFullName(request.fullName());
        userInfo.setBiography(request.biography());
        userInfo.setImage(request.image());
        if (request.gender() != null) {
            userInfo.setGender(Gender.valueOf(request.gender().toUpperCase()));
        }

        userInfoRepo.save(userInfo);
        return SimpleResponse.builder()
                .message("UserInfo успешно сохранено")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public UserInfoResponse findUserInfoByUserId(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        UserInfo info = user.getUserInfo();
        return new UserInfoResponse(
                info.getId(),
                info.getFullName(),
                info.getBiography(),
                info.getGender() != null ? info.getGender().name() : null,
                info.getImage()
        );
    }

    @Override
    @Transactional
    public SimpleResponse update(Long userId, UserInfoRequest request) {
        return saveUserInfo(userId, request);
    }

    @Override
    public SimpleResponse delete(Long id) {
        UserInfo userInfo = userInfoRepo.findById(id).orElseThrow();
        userInfo.getUser().setUserInfo(null);
        userInfoRepo.delete(userInfo);
        return SimpleResponse.builder()
                .message("UserInfo удалено")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    @Transactional
    public SimpleResponse changeImage(Long userId, String newImage) {
        UserInfo info = userInfoRepo.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("UserInfo не найден"));

        info.setImage(newImage);
        return new SimpleResponse("Аватарка обновлена", HttpStatus.OK);
    }

    @Override
    @Transactional
    public SimpleResponse deleteImage(Long userId) {
        UserInfo info = userInfoRepo.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("UserInfo не найден"));

        info.setImage(null);
        return new SimpleResponse("Аватарка удалена", HttpStatus.OK);
    }
}