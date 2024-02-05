package ma.youcode.myrhbackendapi.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    /**
     * extract all claims from jwt token
     * @param authentication {@link Authentication} the authenticated user
     * @return all user authorities as space seperated string
     */
    public String extractClaims(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
    }

    /**
     * generates a jwt token
     * @param authentication {@link Authentication} the authenticated user
     * @param userDetails the authenticated user details
     * @return generated jwt token
     */
    public String generateToken(Authentication authentication, UserDetails userDetails) {
        Instant instant = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(24, ChronoUnit.HOURS))
                .subject(userDetails.getUsername())
                .claim("SCOPE", extractClaims(authentication))
                .build();
        JwtEncoderParameters jwtParameters = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS512).build(),
                jwtClaimsSet
        );
        Jwt jwt = jwtEncoder.encode(jwtParameters);
        return jwt.getTokenValue();
    }

    /**
     * extract all claims in jwt token
     * @param token - user jwt token
     * @return key value pair user claims extracted
     */
    public Map<String, Object> extractAllClaims(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getClaims();
    }

    /**
     * extracts a specific claim from the provided jwt token
     * @param token jwt token to extract claim
     * @param key specifier the key of the claim to extract
     * @return the specified extract claim as object
     */
    public Object extractClaim(String token, String key) {
        Map<String, Object> claims = extractAllClaims(token);
        return claims.get(key);
    }

    /**
     * extracts the subject (username) from the jwt token
     * @param token jwt token to extract username
     * @return username cast to string
     */
    public String extractUserName(String token) {
        return (String) extractClaim(token, "sub");
    }
}
