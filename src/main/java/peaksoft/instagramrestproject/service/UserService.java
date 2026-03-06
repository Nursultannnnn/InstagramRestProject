package peaksoft.instagramrestproject.service;

import peaksoft.instagramrestproject.dto.*;

import java.util.List;

public interface UserService {
    AuthResponse signUp(SignUpRequest signUpRequest);

    AuthResponse signIn(SignInRequest signInRequest);

    SimpleResponse updateProfile(Long userId, SignUpRequest request);

    SimpleResponse deleteUser(Long userId);

    UserProfileResponse userProfile(Long userId);

    List<UserProfileResponse> getAllUsers();

    UserProfileResponse getUserById(Long userId);
}
