package peaksoft.instagramrestproject.service;

import peaksoft.instagramrestproject.dto.*;
import peaksoft.instagramrestproject.dto.auth.request.SignInRequest;
import peaksoft.instagramrestproject.dto.auth.request.SignUpRequest;
import peaksoft.instagramrestproject.dto.auth.response.AuthResponse;
import peaksoft.instagramrestproject.dto.user.UserResponse;

import java.util.List;

public interface UserService {
    AuthResponse signUp(SignUpRequest signUpRequest);

    AuthResponse signIn(SignInRequest signInRequest);

    SimpleResponse updateProfile(Long userId, SignUpRequest request);

    SimpleResponse deleteUser(Long userId);

    UserProfileResponse userProfile(Long userId);

    List<UserResponse> getAllUsers();

    UserProfileResponse getUserById(Long userId);

    List<UserResponse> search(String keyword);
}
