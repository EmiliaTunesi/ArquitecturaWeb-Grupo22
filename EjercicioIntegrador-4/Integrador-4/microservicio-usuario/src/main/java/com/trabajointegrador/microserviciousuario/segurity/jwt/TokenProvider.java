package com.trabajointegrador.microserviciousuario.segurity.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String SECRET = "j7ZookpUTYxclaULynjypGQVKMYXqOXMI+/1sQ2gOV1BF6VOHw6OzYj9RNZY4GcHAE3Igrah3MZ26oLrY/3y4Q==";
    private static final String AUTHORITIES_KEY = "auth";

    private static final String INVALID_JWT_TOKEN = "Invalid JWT token.";

    private final SecretKey key;

    private final JwtParser jwtParser;

    private final int tokenValidityInMilliseconds;

    public TokenProvider() {
        byte[] keyBytes = Decoders.BASE64.decode( SECRET );
        this.key = Keys.hmacShaKeyFor( keyBytes );
        this.jwtParser = Jwts.parser().verifyWith( key ).build();
        this.tokenValidityInMilliseconds = 1000 * 86400; // valido por 1 dia.
    }

    public String createToken( Authentication authentication ) {
        String authorities = authentication.getAuthorities().stream().map( GrantedAuthority::getAuthority ).collect( Collectors.joining(",") );

        long now = ( new Date() ).getTime();
        Date validity = new Date( now + this.tokenValidityInMilliseconds );

        return Jwts
                .builder()
                .subject( authentication.getName() )
                .claim( AUTHORITIES_KEY, authorities )
                .signWith( key )
                .expiration(validity)
                .issuedAt( new Date() )
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken( String authToken ) {
        try {
            final var claims = Jwts.parser().verifyWith(this.key).build().parseSignedClaims(authToken);
            this.checkTokenExpiration( claims );
            return true;
        } catch ( UnsupportedJwtException e ) {
            log.trace(INVALID_JWT_TOKEN, e );
        } catch ( MalformedJwtException e ) {
            log.trace(INVALID_JWT_TOKEN, e );
        } catch ( SignatureException e ) {
            log.trace(INVALID_JWT_TOKEN, e );
        } catch ( IllegalArgumentException e ) {
            log.error("Token validation error {}", e.getMessage());
        }
        return false;
    }

    private void checkTokenExpiration( Jws<Claims> token ){
        try {
            final var payload = token.getPayload();
            if ( payload.getExpiration().before(new Date()) || payload.getIssuedAt().after( new Date((new Date()).getTime() + this.tokenValidityInMilliseconds) ) )
                throw new ExpiredJwtException(null, null, null);
        } catch ( Exception e ) {
            throw new ExpiredJwtException(null, null, null);
        }
    }
}
