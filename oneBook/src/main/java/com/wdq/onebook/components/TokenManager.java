package com.wdq.onebook.components;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenManager {
    //token有效时长
    private long tokenEcpiration = 24 * 60 * 60 * 1000;
    //编码秘钥
    private String tokenSignKey = "wdq_onebook_";

    //1 使用jwt根据用户名生成token
    public String createToken(String username) {
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + tokenEcpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    //2 根据token字符串得到用户信息
    public String getUserInfoFromToken(String token) {
        token = token.substring(1, token.length() - 1);
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }
        return claims.getSubject();
    }
}
