package peaksoft.instagramrestproject.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.instagramrestproject.dto.AuthResponse;
import peaksoft.instagramrestproject.dto.SignInRequest;
import peaksoft.instagramrestproject.dto.SignUpRequest;
import peaksoft.instagramrestproject.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApi {

    private final UserService userService;

    @PostMapping("/signup")
    public AuthResponse signUp(@RequestBody SignUpRequest request) {
        return userService.signUp(request);
    }

    @PostMapping("/signin")
    public AuthResponse signIn(@RequestBody SignInRequest request) {
        return userService.signIn(request);
    }
}