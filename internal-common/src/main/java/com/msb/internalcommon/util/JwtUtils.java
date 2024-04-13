package com.msb.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.msb.internalcommon.dto.TokenResult;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    // 盐
    private static final String SIGN = "msbtaxi";

    private static final String JWT_KEY_PHONE = "passengerPhone";

    // 乘客是1，司机是2
    private static final String JWT_KEY_IDENTITY = "identity";

    // 生成token
    public static String generateToken(String passengerPhone,
                                       String identity) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE, passengerPhone);
        map.put(JWT_KEY_IDENTITY, identity);
        // token过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date date = calendar.getTime();

        JWTCreator.Builder builder = JWT.create();
        // 整合map
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        // 整合过期时间
        builder.withExpiresAt(date);
        // 生成token
        String sign = builder.sign(Algorithm.HMAC256(SIGN));
        return sign;
    }

    // 解析token
    public static TokenResult parseToken(String token) throws UnsupportedEncodingException {
        DecodedJWT verify =
                JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = verify.getClaim(JWT_KEY_PHONE).asString();
        String identity = verify.getClaim(JWT_KEY_IDENTITY).asString();
        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(phone);
        tokenResult.setIdentity(identity);
        return tokenResult;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = generateToken("13955551234", "1");
        System.out.println("生成的token是 " + s);
        System.out.println("解析-------------------");
        System.out.println("解析的phone是 " + parseToken(s).getPhone());
        System.out.println("解析的身份是 " + parseToken(s).getIdentity());
    }
}
