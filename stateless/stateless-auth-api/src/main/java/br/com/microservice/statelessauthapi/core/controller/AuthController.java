package br.com.microservice.statelessauthapi.core.controller;

import br.com.microservice.statelessauthapi.core.dto.AuthRequest;
import br.com.microservice.statelessauthapi.core.dto.TokenDto;
import br.com.microservice.statelessauthapi.core.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService service;

    @PostMapping("login")
    public TokenDto login(@RequestBody AuthRequest request){
        return service.login(request);
    }

    @PostMapping("token/validate")
    public TokenDto login(@RequestHeader String accessToken){
        return service.validateToken(accessToken);
    }
}
