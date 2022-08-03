package cn.linshiyou.financialFinalContest.login.util;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

public class JwtHelper {

    /**
     * 过期时间
     */
    private static long tokenExpiration = 24 * 60 * 60 * 1000;
    /**
     * 签名秘钥
     */
    private static String tokenSignKey = "financial";

    /**
     * 根据参数生成token
     * @param userId
     * @param userName
     * @return
     */
    public static String createToken(Long userId, String userName) {
        //对秘钥加密
        SecretKey key = generalKey();

        String token = Jwts.builder()
                .setSubject("USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("userId", userId)
                .claim("userName", userName)
                .signWith(SignatureAlgorithm.HS512, key)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    /**
     * 根据token字符串得到用户id
     * @param token
     * @return
     */
    public static Long getUserId(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        SecretKey key = generalKey();
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Integer userId = (Integer) claims.get("userId");
        return userId.longValue();
    }

    /**
     * 根据token得到用户名称
     * @param token
     * @return
     */
    public static String getUserName(String token) {
        SecretKey key = generalKey();
        if (StringUtils.isEmpty(token)) {
            return "";
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String) claims.get("userName");
    }

    /**
     * 生成加密后的秘钥 secretKey
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(tokenSignKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

}

