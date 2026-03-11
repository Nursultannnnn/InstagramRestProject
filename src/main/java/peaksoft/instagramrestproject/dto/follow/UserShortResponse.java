package peaksoft.instagramrestproject.dto.follow;

public record UserShortResponse(
        Long id,
        String username,
        String image
) {}