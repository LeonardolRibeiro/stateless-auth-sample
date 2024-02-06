package br.com.microservice.statelessauthapi.core.service;

import br.com.microservice.statelessauthapi.core.model.User;
import br.com.microservice.statelessauthapi.infra.exception.AuthenticationException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.xml.bind.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class JwtService {

    public static final int ONE_DAY_IN_HOURS = 24;
    public static final String EMPTY_SPACE = "";
    public static final int TOKEN_INDEX = 1;
    @Value("${app.token.secret-key}")
    private String secretKey;

    public String createToken(User user) {

        var data = new HashMap<String, String>();
        data.put("id", user.getId().toString());
        data.put("username", user.getUsername());
        return Jwts.builder()
                .setClaims(data)
                .setExpiration(generationExpiresAt())
                .signWith(generateSign())
                .compact();
    }

    private Date generationExpiresAt() {
        return Date.from(
                LocalDateTime.now()
                        .plusHours(ONE_DAY_IN_HOURS)
                        .atZone(ZoneId.systemDefault()).toInstant()
        );
    }

    private SecretKey generateSign(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public void validateAccessToken(String token) throws ValidationException {
        var accessToken = extractToken(token);
        try {
            Jwts
                    .parserBuilder()
                    .setSigningKey(generateSign())
                    .build()
                    .parseClaimsJwt(accessToken)
                    .getBody();
        } catch (Exception e) {
            throw new AuthenticationException("Invalid token " + e.getMessage());
        }
    }

    private String extractToken(String token) throws ValidationException {
        if (isEmpty(token))
            throw new ValidationException("The access token was not informed.");

        if(token.contains(EMPTY_SPACE))
            return token.split(EMPTY_SPACE)[TOKEN_INDEX];

        return token;
    }

}
