package peaksoft.instagramrestproject.dto.post;

public record PostRequest(
        String title,
        String description,
        String imageUrl // Пока будем передавать просто ссылку строкой
) {}