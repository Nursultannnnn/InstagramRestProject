package peaksoft.instagramrestproject.dto.user;

import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String username,
        String image,
        int countSubscribers,
        int countSubscriptions,
        String fullName
) {}