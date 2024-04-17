package com.msb.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
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

    private static final String JWT_TOKEN_TYPE = "tokenType";

    private static final String JWT_TOKEN_TIME = "tokenTime";

    // 生成token
    public static String generateToken(String passengerPhone,
                                       String identity,
                                       String tokenType) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE, passengerPhone);
        map.put(JWT_KEY_IDENTITY, identity);
        map.put(JWT_TOKEN_TYPE, tokenType);
        // token过期时间
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, 1);
//        Date date = calendar.getTime();
        // 防止每次生成的token都一样
        map.put(JWT_TOKEN_TIME, Calendar.getInstance().getTime().toString());

        JWTCreator.Builder builder = JWT.create();
        // 整合map
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        // 整合过期时间
        // builder.withExpiresAt(date);
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

    /**
     * 主要判断token是否异常
     *
     * @param token
     * @return
     */
    public static TokenResult checkToken(String token) {
        TokenResult tokenResult = null;
        // 只要有异常，就直接报错即可，无需关注到底是什么错误
        try {
            tokenResult = JwtUtils.parseToken(token);
        } catch (Exception e) {

        }
        return tokenResult;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = generateToken("13955551234", "1", "access_token");
        System.out.println("生成的token是 " + s);
        System.out.println("解析-------------------");
        System.out.println("解析的phone是 " + parseToken(s).getPhone());
        System.out.println("解析的身份是 " + parseToken(s).getIdentity());
    }
}
