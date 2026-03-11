package peaksoft.instagramrestproject.dto.auth.request;

public record SignUpRequest(

        String username,
        String email,
        String password,
        String fullName,
        String phoneNumber
) {
}