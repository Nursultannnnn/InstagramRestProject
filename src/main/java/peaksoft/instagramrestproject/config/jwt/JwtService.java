package peaksoft.instagramrestproject.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import peaksoft.instagramrestproject.entity.User;
import peaksoft.instagramrestproject.repo.UserRepo;

import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class JwtService {
    private final UserRepo userRepo;

    @Value("${security.secret.key}")
    private String secretKey;

    // create token
    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole().name())
                .withIssuedAt(ZonedDateTime.now().toInstant())
                .withExpiresAt(ZonedDateTime.now().plusHours(24).toInstant())
                .sign(algorithm);
    }

    // proverka token
    public User verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String email = decodedJWT.getClaim("email").asString();
        return userRepo.getUserByEmail(email).orElseThrow(
                () -> new NoSuchElementException(
                        String.format("user with email doesnt exists", email)
                )
        );
    }
}
