package peaksoft.instagramrestproject.dto.userinfo;

public record UserInfoRequest(
        String fullName,
        String biography,
        String gender,
        String image
) {}