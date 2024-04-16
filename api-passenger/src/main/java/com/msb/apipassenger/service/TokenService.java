package com.msb.apipassenger.service;

import com.msb.internalcommon.constant.CommonStatusEnum;
import com.msb.internalcommon.constant.TokenConstants;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.dto.TokenResult;
import com.msb.internalcommon.response.TokenResponse;
import com.msb.internalcommon.util.JwtUtils;
import com.msb.internalcommon.util.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {
    public ResponseResult refreshToken(String refreshTokenSrc) throws UnsupportedEncodingException {
        // 解析refreshToken
        TokenResult tokenResult = JwtUtils.checkToken(refreshTokenSrc);
        if (tokenResult == null) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getValue());
        }
        String phone = tokenResult.getPhone();
        String identity = tokenResult.getIdentity();

        // 读取redis中的refreshToken
        String refreshTokenKey = RedisPrefixUtils.generateTokenKey(phone, identity,
                TokenConstants.REFRESH_TOKEN_TYPE);
        String refreshTokenRedis = StringRedisTemplate.opsForValue().get(refreshTokenKey);

        // 校验refreshToken
        if (StringUtils.isBlank(refreshTokenRedis) || !refreshTokenSrc.trim().equals(refreshTokenRedis.trim())) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getValue());
        }

        // 生成双token
        String accessToken = JwtUtils.generateToken(phone, identity,
                TokenConstants.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generateToken(phone, identity,
                TokenConstants.REFRESH_TOKEN_TYPE);
        String accessTokenKey = RedisPrefixUtils.generateTokenKey(phone,
                identity, TokenConstants.ACCESS_TOKEN_TYPE);
        StringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30
                , TimeUnit.DAYS);
        StringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31
                , TimeUnit.DAYS);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setRefreshToken(refreshToken);
        tokenResponse.setAccessToken(accessToken);
        return ResponseResult.success(tokenResponse);
    }
}
