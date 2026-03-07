package peaksoft.instagramrestproject.dto;

import lombok.Builder;
import peaksoft.instagramrestproject.enums.Role;

@Builder
public record AuthResponse(
        String email,
        String token,
        Role role
) {
}
