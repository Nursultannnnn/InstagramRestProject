package peaksoft.instagramrestproject.dto.auth.response;

import lombok.Builder;
import peaksoft.instagramrestproject.enums.Role;

@Builder
public record AuthResponse(
        String email,
        String token,
        Role role
) {
}
