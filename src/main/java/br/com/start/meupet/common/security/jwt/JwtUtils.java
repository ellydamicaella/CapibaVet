package br.com.start.meupet.common.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import br.com.start.meupet.common.utils.BirthDayUtils;
import br.com.start.meupet.common.valueobjects.Email;
import br.com.start.meupet.partner.model.Partner;
import br.com.start.meupet.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.start.meupet.common.service.AuthenticableDetailsImpl;
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


    public String generateTokenFromAuthenticableDetailsImpl(
            AuthenticableDetailsImpl authenticableDetails
    ) {
        return Jwts.builder().header().add("typ", "JWT").and().subject(authenticableDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + this.jwtExpirationMs))
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    public String generateTokenForUserVerifyAccount(
            User user
    ) {
        return Jwts.builder().header().add("typ", "JWT").and().subject(user.getEmail().toString())
                .issuedAt(new Date())
                .claim("name", user.getName())
                .claim("socialName", user.getSocialName())
                .claim("phoneNumber", user.getPhoneNumber().toString())
                .claim("password", user.getPassword())
                .claim("document", user.getPersonalRegistration().getDocument())
                .claim("documentType", user.getPersonalRegistration().getType().toString())
                .claim("birthDate", BirthDayUtils.formatDateOfBirth(user.getDateOfBirth()))
                .claim("typeUser", "USER")
                .expiration(new Date(new Date().getTime() + this.jwtExpirationMs))
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    public String generateTokenForPartnerVerifyAccount(
            Partner partnerRequest
    ) {
        return Jwts.builder().header().add("typ", "JWT").and().subject(partnerRequest.getEmail().toString())
                .issuedAt(new Date())
                .claim("name", partnerRequest.getName())
                .claim("phoneNumber", partnerRequest.getPhoneNumber().toString())
                .claim("password", partnerRequest.getPassword())
                .claim("document", partnerRequest.getPersonalRegistration().getDocument())
                .claim("documentType", partnerRequest.getPersonalRegistration().getType().toString())
                .claim("typeUser", "PARTNER")
                .expiration(new Date(new Date().getTime() + this.jwtExpirationMs))
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    public String generateTokenForPasswordRecovery(
            Email email
    ) {
        return Jwts.builder().header().add("typ", "JWT").and().subject(email.toString())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + this.jwtExpirationMs))
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    public SecretKey getSigningKey() {
        //log.info("jwtSecret: {}", this.jwtSecret);
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
            log.error("Token invalido : {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Token expirado : {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Token nao suportado : {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Token argumento invalido : {}", e.getMessage());
        }

        return false;
    }
}
