package peaksoft.instagramrestproject.dto.follow;

public record UserSearchResponse(
        Long id,
        String username,
        String image,
        String fullName
) {}
