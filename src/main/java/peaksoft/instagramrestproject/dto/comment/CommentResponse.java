package peaksoft.instagramrestproject.dto.comment;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String comment,
        LocalDateTime createdAt,
        String username,
        int likeCount
) {
}