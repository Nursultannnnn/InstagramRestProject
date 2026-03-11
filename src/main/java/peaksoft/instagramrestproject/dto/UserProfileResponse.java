package peaksoft.instagramrestproject.dto;

import lombok.Builder;
import peaksoft.instagramrestproject.dto.post.PostResponse;

import java.util.List;

@Builder
public record UserProfileResponse(
        Long id,
        String username,
        String image,
        String fullName,
        String biography,
        int countSubscribers,
        int countSubscriptions,
        List<PostResponse> posts // Мы добавим PostResponse чуть позже, когда займемся постами
) {
}