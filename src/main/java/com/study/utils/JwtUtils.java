package com.study.utils;

import cn.hutool.json.JSONUtil;
import io.jsonwebtoken.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.util.Calendar;
import java.util.Date;

/**
 * jwt工具类
 */
@Slf4j
@Data
@Component
public class JwtUtils {
    //密钥
    public static String SECRET = "ZOjgWLPRWrEy1svvtbYQ2TK1F1HuueCZ0qsCVJCLGZ1eQ5IckmTmUiRJq91y7ut";
    private static String base64Key = DatatypeConverter.printBase64Binary(SECRET.getBytes());
    private static byte[] secretBytes = DatatypeConverter.parseBase64Binary(base64Key);

    //创建token
    public static String createToken(String s) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 24 * 60 * 60 * 7);
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("alg", "HS512")
                .setSubject(s)
                .setIssuedAt(new Date())
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.HS512, secretBytes);
        return builder.compact();
    }


    //校验jwt
    public static Claims parseToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretBytes)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
//            System.out.println("getIssuer : " + claims.getSubject());
        } catch (Exception e) {
            log.error("jwt match error:{}", e);
            return null;
        }
        return claims;
    }


    public static void main(String[] args) {

        String token = createToken(JSONUtil.toJsonStr(JSONUtil.parseObj("{\"mobile\": \"15500001234\",\"sysName\": \"青岛国信\",\"pinyin\":\"dandiandengluceshiyonghu\",\"userName\": \"单点登录测试用户\",\"sysId\":\"357b1469-f383-4f27-a10d-6afdfc934b7a\"}")));
        System.out.println(token);
        System.out.println(parseToken(token).toString());
        System.out.println(parseToken("eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2ODM2MjAxNzEsInN1YiI6IntcIm1vYmlsZVwiOlwiMTU1MDAwMDEyMzRcIixcInN5c05hbWVcIjpcIumdkuWym-WbveS_oeiDtuW3nua5vuesrOS6jOa1t-W6lemap-mBk1wiLFwicGlueWluXCI6XCJkYW5kaWFuZGVuZ2x1Y2VzaGl5b25naHVcIixcInVzZXJOYW1lXCI6XCLljZXngrnnmbvlvZXmtYvor5XnlKjmiLdcIixcInN5c0lkXCI6XCIzNTdiMTQ2OS1mMzgzLTRmMjctYTEwZC02YWZkZmM5MzRiN2FcIn0ifQ.aK-itDK_lO9FKXKvv_rg6Iuo_tlcL6xSy6V6ZT7_lEGOgc1EWN_I1KM3jsmfdpEL5-OaIgMFII_nG-Mf4WLedg"));
//        verifyToken("eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2ODM2MjAxNzEsInN1YiI6IntcIm1vYmlsZVwiOlwiMTU1MDAwMDEyMzRcIixcInN5c05hbWVcIjpcIumdkuWym-WbveS_oeiDtuW3nua5vuesrOS6jOa1t-W6lemap-mBk1wiLFwicGlueWluXCI6XCJkYW5kaWFuZGVuZ2x1Y2VzaGl5b25naHVcIixcInVzZXJOYW1lXCI6XCLljZXngrnnmbvlvZXmtYvor5XnlKjmiLdcIixcInN5c0lkXCI6XCIzNTdiMTQ2OS1mMzgzLTRmMjctYTEwZC02YWZkZmM5MzRiN2FcIn0ifQ.aK-itDK_lO9FKXKvv_rg6Iuo_tlcL6xSy6V6ZT7_lEGOgc1EWN_I1KM3jsmfdpEL5-OaIgMFII_nG-Mf4WLedg");
    }
}
