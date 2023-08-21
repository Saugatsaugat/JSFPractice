package com.saugat.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

/**
 *
 * @author saugat
 */
public class TokenManager {

    private static final long EXPIRATION_TIME = 600000; //1 min//10min = 1000*10*60
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken(String subject) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);

        String token = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    public static String verifyToken(String token) {
        
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        try {
            Claims claims = parser.parseClaimsJws(token).getBody();
            System.out.println("");
            String userId = claims.getSubject();
            return userId;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
