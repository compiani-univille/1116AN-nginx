package com.example.gateway_service.infrastructure.security;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.gateway_service.domain.user.vo.RoleType;

import reactor.core.publisher.Mono;

@Component
public class AuthorizationFilter implements WebFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private static final Map<String, RoleType> routeRoles = Map.of(
        "/demo1/waiter", RoleType.WAITER,
        "/demo1/customer", RoleType.CUSTOMER
    );

    private boolean isAuthorized(String path, RoleType role) {
        for (Map.Entry<String, RoleType> entry : routeRoles.entrySet()) {
            if (path.startsWith(entry.getKey())) {
                return role.covers(entry.getValue());
            }
        }

        return false;
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

        return exchange.getResponse().setComplete();
    }

   @Override
   public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    String path = request.getPath().toString();

    // Se a rota não estiver na lista de protegidos, segue com a requisição
    if (routeRoles.entrySet().stream().noneMatch(entry -> path.startsWith(entry.getKey()))) {
        return chain.filter(exchange);
    }

    // Verifica se o token está registrado no header como "Authorization" e começa com "Bearer "
    String authHeader = request.getHeaders().getFirst("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return unauthorized(exchange);
    }

    // Decodifica o token
    String token = authHeader.substring(7);
    DecodedJWT jwt;
    try {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
        JWTVerifier verifier = JWT.require(algorithm).build();
        jwt = verifier.verify(token);
    } catch (Exception e) {
        return unauthorized(exchange);
    }

    // Verifica se o token passa do é o token de "access", o refresh não pode ser utilizado
    String tokenType = jwt.getClaim("type").asString();
    if (!tokenType.equals("access")) {
        return unauthorized(exchange);
    }

    // Busca a role do usuario
    String userRoleType = jwt.getClaim("role").asString();
    RoleType role;
    try {
        role = RoleType.valueOf(userRoleType);
    } catch (Exception e) {
        return unauthorized(exchange);
    }

    // Verifica a permissão para a rota
    if (!isAuthorized(path, role)) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }

    return chain.filter(exchange);
   }

    
}
