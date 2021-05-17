package com.nitish.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    @Value("${app.secret}")
    private String secret;


    public boolean validateToken(String token, String username){
        String tokenusername = getUsername(token);
        return (username.equals(tokenusername) && !isTokenExp(token));
    }


    public boolean isTokenExp(String token){
        Date expDate = getExpiryDate(token);
        return expDate.before(new Date(System.currentTimeMillis()));
    }

    public String getUsername(String token){
        return getClaims(token).getSubject();
    }

    public Date getExpiryDate(String token){

        return getClaims(token).getExpiration();
    }


    public Claims getClaims(String token){
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
    }


    public String  generateToken(String subject){
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer("Nitish")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15)))
                .signWith(SignatureAlgorithm.HS512,secret.getBytes()).compact();
    }


}
