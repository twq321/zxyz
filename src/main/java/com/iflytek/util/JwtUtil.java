package com.iflytek.util;

import com.iflytek.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import com.iflytek.dto.JwtPayload;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "your_secret_key";
    private final long EXPIRATION = 1000 * 60 * 60; // 1小时

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getLogintext())
                .claim("userId", user.getUserid())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

//    public String getUsernameFromToken(String token) {
//        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
//    }
    public JwtPayload getPayloadFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        JwtPayload payload = new JwtPayload();
        payload.setLogintext(claims.getSubject());
        payload.setUserid(claims.get("userId", Integer.class));


        return payload;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
