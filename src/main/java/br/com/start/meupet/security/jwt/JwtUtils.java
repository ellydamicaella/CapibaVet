package br.com.start.meupet.security.jwt;

import br.com.start.meupet.service.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${projeto.jwtSecret}")
    private String jwtSecret;

    @Value("${projeto.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateTokenFromUserDetailsImpl(UserDetailsImpl userDetail) {
        return Jwts.builder().subject(userDetail.getUsername()).issuedAt(new Date()).expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(getSigningKey(), Jwts.SIG.HS512).compact();
    }

    public SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsernameToken(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();
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
