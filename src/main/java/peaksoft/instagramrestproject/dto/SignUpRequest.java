package peaksoft.instagramrestproject.dto;

public record SignUpRequest(

        String username,
        String email,
        String password,
        String fullName,
        String phoneNumber
) {
}