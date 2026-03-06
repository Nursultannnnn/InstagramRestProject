package peaksoft.instagramrestproject.dto;

public record PostResponse(
        Long id,
        String imageURL,
        String description
) {
}