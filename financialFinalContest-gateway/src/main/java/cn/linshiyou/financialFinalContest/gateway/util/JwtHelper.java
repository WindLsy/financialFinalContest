package cn.linshiyou.financialFinalContest.gateway.util;

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
     * 解析
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        return Jwts.parser()
                .setSigningKey(tokenSignKey)
                .parseClaimsJws(jwt)
                .getBody();
    }


    /**
     * 根据参数生成token
     * @param userId
     * @param userName
     * @return
     */
    public static String createToken(String userId, String userName) {
        //对秘钥加密

        String token = Jwts.builder()
                .setSubject("USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("userId", userId)
                .claim("userName", userName)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

}

