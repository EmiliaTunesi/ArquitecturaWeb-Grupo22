package com.trabajointegrador.gateway.config;

import com.trabajointegrador.gateway.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final JwtUtils jwtUtils;
    private final RestTemplate restTemplate;

    public AuthFilter(JwtUtils jwtUtils, RestTemplate restTemplate) {
        super(Config.class);
        this.jwtUtils = jwtUtils;
        this.restTemplate = restTemplate;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // 1. Verificar Header Authorization
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String token = authHeader.replace("Bearer ", "");

            // 2. Validar firma del Token
            if (!jwtUtils.validateToken(token)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            // 3. Validar ROL (Si la ruta lo pide en el YAML)
            if (config.role != null) {
                Claims claims = jwtUtils.getClaims(token);
                Object rolesObject = claims.get("auth");
                String rolesString = String.valueOf(rolesObject);

                if (!rolesString.contains(config.role)) {
                    return onError(exchange, HttpStatus.FORBIDDEN);
                }
            }

            // 4. Validar PREMIUM para rutas del chat
            String path = exchange.getRequest().getPath().toString();
            if (path.startsWith("/api/chat")) {
                try {
                    Claims claims = jwtUtils.getClaims(token);
                    Long userId = claims.get("userId", Long.class);

                    String url = "http://localhost:8081/api/usuarios/" + userId + "/tiene-premium";
                    Boolean tienePremium = restTemplate.getForObject(url, Boolean.class);

                    if (tienePremium == null || !tienePremium) {
                        return onError(exchange, HttpStatus.FORBIDDEN);
                    }
                } catch (Exception e) {
                    return onError(exchange, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    public static class Config {
        private String role;
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
}