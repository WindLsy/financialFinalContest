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

}

