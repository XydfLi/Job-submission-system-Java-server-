package com.api.JWT;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 进行JWT操作
 *
 * @author 李星源
 * @version 1.0
 */
public class JwtHelper {

    //任意不变字符串
    public static final String secret="22640E66C97339C027F77759025CAD17";

    /**
     * 由secret生成加密key
     *
     * @param secret 当前用户的secret（MD5字符串）
     * @return SecretKey对象
     */
    public static SecretKey generalKey(String secret){
        byte[] enbytes=Base64.encodeBase64Chunked(secret.getBytes());
        SecretKey key=new SecretKeySpec(enbytes,0,enbytes.length,"AES");
        return key;
    }

    /**
     * Base64解密
     *
     * @param pwd 需要解密字符串
     * @return 解密后字符串
     */
    public static String decodeStr(String pwd)
    {
        byte[] debytes=Base64.decodeBase64(new String(pwd).getBytes());
        return new String(debytes);
    }

    /**
     * Base64加密
     *
     * @param pwd 需要加密字符串
     * @return 加密后字符串
     */
    public static String encodeStr(String pwd)
    {
        byte[] enbytes=Base64.encodeBase64Chunked(pwd.getBytes());
        return new String(enbytes);
    }

    /**
     * 解密jwt
     *
     * @param jsonWebToken token
     * @return Claims对象
     */
    public static Claims parseJWT(String jsonWebToken) {
        SecretKey key=generalKey(secret);
        Claims claims=Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jsonWebToken)
                .getBody();
        return claims;
    }

    /**
     * 创建jwt
     *
     * @param uid 用户id
     * @param subject 用户信息转成的json
     * @param TTLMillis 过期时间毫秒
     * @param secret 字符串
     * @return JWT
     */
    public static String createJWT(String uid,String subject,long TTLMillis, String secret,String role) {
        //使用HS256算法
        SignatureAlgorithm signatureAlgorithm=SignatureAlgorithm.HS256;
        long nowMillis=System.currentTimeMillis();
        Date now=new Date(nowMillis);

        // 生成签名密钥
        Key signingKey=generalKey(secret);

        JwtBuilder builder=Jwts.builder()
                .setHeaderParam("type", "JWT")
                .claim("userid", uid)
                .setIssuedAt(now)//在什么时候签发的(UNIX时间)
                .setSubject(subject)//该JWT所面向的用户
                .setIssuer("LXY")//该JWT的签发者
                .setAudience(role)//设置角色
                .setId("1")//设置id
                .signWith(signatureAlgorithm, signingKey);

        // 添加Token过期时间
        if (TTLMillis >= 0) {
            long expMillis=nowMillis+TTLMillis;
            Date exp=new Date(expMillis);
            builder.setExpiration(exp);//exp(expires): 什么时候过期，这里是一个Unix时间戳，是否使用是可选的；
            //.setNotBefore(now);//nbf (Not Before)：如果当前时间在nbf里的时间之前，则Token不被接受；一般都会留一些余地，比如几分钟；，是否使用是可选的；
        }

        // 生成JWT
        return builder.compact();
    }

    /**
     * 生成subject信息
     *
     * @param user
     * @return
     */
    public static String generalSubject(JwtUser user){
        JSONObject jo=new JSONObject(user);
        return jo.toString();
    }

    /**
     * 登入后的用户，生成token
     * 有效时间：3个小时
     *
     * @param uvo JwtUser对象
     * @return token
     */
    public static String createJWT(JwtUser uvo,String role){
        uvo.setLoginTime(System.currentTimeMillis());
        String subject=JwtHelper.generalSubject(uvo);
        String accessToken=JwtHelper.createJWT(uvo.getId(), subject, 3*60*60*1000, secret,role);
        return accessToken;
    }

}
