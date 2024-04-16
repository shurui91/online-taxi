package com.msb.apipassenger.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.msb.internalcommon.constant.TokenConstants;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.dto.TokenResult;
import com.msb.internalcommon.util.JwtUtils;
import com.msb.internalcommon.util.RedisPrefixUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean res = true;
        String resString = "";
        String token = request.getHeader("Authorization");
        TokenResult tokenResult = null;
        // 解析token
        try {
            tokenResult = JwtUtils.parseToken(token);
        } catch (SignatureVerificationException e) {
            resString = "token sign error";
            res = false;
        } catch (TokenExpiredException e) {
            resString = "token expired";
            res = false;
        } catch (AlgorithmMismatchException e) {
            resString = "token algorithm mismatch";
            res = false;
        } catch (Exception e) {
            resString = "token invalid";
            res = false;
        }

        if (tokenResult == null) {
            resString = "token invalid";
            res = false;
        } else {
            // 拼接key
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            String tokenKey = RedisPrefixUtils.generateTokenKey(phone,
                    identity, TokenConstants.ACCESS_TOKEN_TYPE);
            // 从redis中获取token
            String tokenRedis = stringRedisTemplate.opsForValue().get(tokenKey);
            if (StringUtils.isBlank(tokenRedis)) {
                resString = "token invalid";
                res = false;
            } else {
                if (!token.trim().equals(tokenRedis.trim())) {
                    resString = "token invalid";
                    res = false;
                }
            }
        }

        // 比较我们传入的token和redis中的token是否一致

        if (!res) {
            PrintWriter out = response.getWriter();
            out.println(JSONObject.fromObject(ResponseResult.fail(resString)).toString());
        }
        return res;
    }
}
