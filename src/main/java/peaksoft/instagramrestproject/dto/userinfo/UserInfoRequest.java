package peaksoft.instagramrestproject.dto.userinfo;

public record UserInfoRequest(
        String fullName,
        String biography,
        String gender, // Будем принимать строкой ("MALE", "FEMALE")
        String image // Ссылка на аватарку
) {}