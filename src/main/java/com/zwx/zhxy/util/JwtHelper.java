package com.zwx.zhxy.util;


import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * token口令生成工具 JwtHelper
 * @author zwx
 */
public class JwtHelper {

    //有效时间24小时
    private static long tokenExpiration = 24*60*60*1000;
    //加盐
    private static String tokenSignKey = "123456";

    /**
     * 生成token字符串
     * @param userId
     * @param userType
     * @return
     */
    public static String createToken(Long userId, Integer userType) {
        String token = Jwts.builder()

                .setSubject("YYGH-USER")

                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))

                .claim("userId", userId)
//                .claim("userName", userName)
                .claim("userType", userType)

                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    /**
     * 从token字符串获取userid
     * @param token
     * @return
     */
    public static Long getUserId(String token) {
        if(StringUtils.isEmpty(token)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Integer userId = (Integer)claims.get("userId");
        return userId.longValue();
    }

    /**
     * 从token字符串获取userType
     * @param token
     * @return
     */
    public static Integer getUserType(String token) {
        if(StringUtils.isEmpty(token)) return null;
        Jws<Claims> claimsJws
                = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (Integer)(claims.get("userType"));
    }

    /**
     * 从token字符串获取userName
     * @param token
     * @return
     */
    public static String getUserName(String token) {
        if(StringUtils.isEmpty(token)) return "";
        Jws<Claims> claimsJws
                = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("userName");
    }

    /**
     * 判断token是否有效
     * @param token
     * @return
     */
    public static boolean isExpiration(String token){
        try {
            boolean isExpire = Jwts.parser()
                    .setSigningKey(tokenSignKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration().before(new Date());
            //没有过期，有效，返回false
            return isExpire;
        }catch(Exception e) {
            //过期出现异常，返回true
            return true;
        }
    }


    /**
     * 刷新Token
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(tokenSignKey)
                    .parseClaimsJws(token)
                    .getBody();
            refreshedToken = JwtHelper.createToken(getUserId(token), getUserType(token));
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

//    public static void main(String[] args) {
//        String token = JwtHelper.createToken(1L, 1);
//        System.out.println(token);
//        System.out.println(JwtHelper.getUserId(token));
//        System.out.println(JwtHelper.getUserName(token));
//    }
}
