package peaksoft.instagramrestproject.service;

import peaksoft.instagramrestproject.dto.SimpleResponse;
import peaksoft.instagramrestproject.dto.userinfo.UserInfoRequest;
import peaksoft.instagramrestproject.dto.userinfo.UserInfoResponse;

public interface UserInfoService {
    SimpleResponse saveUserInfo(Long userId, UserInfoRequest userInfoRequest); // Создать
    UserInfoResponse findUserInfoByUserId(Long userId); // Найти по ID
    SimpleResponse update(Long userId, UserInfoRequest request); // Обновить по ID
    SimpleResponse delete(Long id);
    SimpleResponse changeImage(Long userId, String newImage);
    SimpleResponse deleteImage(Long userId);
}
