package br.com.start.meupet.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.start.meupet.service.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public final class JwtUtils {

    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

    private final String jwtSecret = "eyJhbGciOiJIUzUxMiJ9eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTczMDQ5Njc4OCwiaWF0IjoxNzMwNDk2Nzg4fQPihcxN4AJlFW8EFPaSQGLL1r3ltbZBv0nOI1BqflmIU98sFMfqc0Sy9iyVHphSEuHVhwciw97OcKApEg";

    private final int jwtExpirationMs = 600000;


    public String generateTokenFromUserDetailsImpl(UserDetailsImpl userDetail) {
        return Jwts.builder().header().add("typ", "JWT").and().subject(userDetail.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + this.jwtExpirationMs))
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    public String generateTokenFromUserVerifyDetailsImpl(
            String email,
            String name,
            String phone_number,
            String password) {

        return Jwts.builder().header().add("typ", "JWT").and().subject(email)
                .issuedAt(new Date())
                .claim("name", name)
                .claim("phone_number", phone_number)
                .claim("password", password)
                .expiration(new Date(new Date().getTime() + this.jwtExpirationMs))
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    public SecretKey getSigningKey() {
        log.info("jwtSecret: {}", this.jwtSecret);
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.jwtSecret));
    }

    public String getUsernameToken(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public Claims getParsedToken(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Token invalido " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("Token expirado " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Token nao suportado " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Token argumento invalido " + e.getMessage());
        }

        return false;
    }
}
