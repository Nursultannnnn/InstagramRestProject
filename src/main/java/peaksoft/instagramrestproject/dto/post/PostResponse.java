package peaksoft.instagramrestproject.dto.post;

import lombok.Builder;

@Builder
public record PostResponse(
        Long id,
        String imageURL, // Ссылка на картинку поста
        String description // Описание
) {
}