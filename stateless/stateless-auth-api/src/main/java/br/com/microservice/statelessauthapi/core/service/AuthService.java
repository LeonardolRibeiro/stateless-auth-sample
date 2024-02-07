package br.com.microservice.statelessauthapi.core.service;

import br.com.microservice.statelessauthapi.core.dto.AuthRequest;
import br.com.microservice.statelessauthapi.core.dto.TokenDto;
import br.com.microservice.statelessauthapi.core.repository.UserRepository;
import br.com.microservice.statelessauthapi.infra.exception.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository repository;

    public TokenDto login(AuthRequest request){
        var user = repository
                .findByUsername(request.username()).orElseThrow(() -> new ValidationException("User not found"));
        var accessToken = jwtService.createToken(user);
        validatePassword(request.password(), user.getPassword());
        return new TokenDto(accessToken);
    }


    private void validatePassword(String rawPassword, String encodedPassword){
        if(isEmpty(rawPassword))
            throw new ValidationException("The password must be informed");

        if(!passwordEncoder.matches(rawPassword, encodedPassword))
            throw new ValidationException("The password is incorrect");
    }

    public TokenDto validateToken(String accessToken) {
        validateExistingToken(accessToken);
        jwtService.validateAccessToken(accessToken);
        return new TokenDto(accessToken);
    }

    private void validateExistingToken(String accessToken){
        if(isEmpty(accessToken))
            throw new ValidationException("The access token must be informed!");
    }




}
