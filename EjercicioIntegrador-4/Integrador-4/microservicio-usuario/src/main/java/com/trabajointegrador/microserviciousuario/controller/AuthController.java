package com.trabajointegrador.microserviciousuario.controller;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.trabajointegrador.microserviciousuario.dto.LoginDTO;
import com.trabajointegrador.microserviciousuario.segurity.jwt.TokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/authenticate")
@RequiredArgsConstructor
public class AuthController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginDTO request ) {



        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        return ResponseEntity.ok(new JWTToken(jwt));
    }

    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}

