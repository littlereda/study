package com.study.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * jwt工具类
 */
@Slf4j
@Data
@Component
public class JwtService {

    //密钥
//    @Value("${jwt.SECRET}")
    public String SECRET;
    //Authorization
    private String header;

    //创建token
    public String createToken(String s){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,24*60*60*7);
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("alg","HS512")
                .setSubject(s)
                .setIssuedAt(new Date())
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.HS512,SECRET);
        return builder.compact();
    }

    //校验jwt
    public Claims parseToken(String token){
        try{
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            log.error("jwt match error:{}",e);
            return null;
        }
    }

    //判断token是否过期
    public boolean judgeTokenExpiration(Date expiration){
        return expiration.before(new Date());
    }
}

