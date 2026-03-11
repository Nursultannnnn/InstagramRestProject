package peaksoft.instagramrestproject.dto.auth.request;

public record SignInRequest(
        String email,
        String password
) {
}
