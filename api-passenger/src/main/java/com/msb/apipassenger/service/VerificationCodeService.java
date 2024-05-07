package com.msb.apipassenger.service;

import com.msb.apipassenger.remote.ServicePassengerUserClient;
import com.msb.apipassenger.remote.ServiceVerificationcodeClient;
import com.msb.internalcommon.constant.CommonStatusEnum;
import com.msb.internalcommon.constant.IdentityConstants;
import com.msb.internalcommon.constant.TokenConstants;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.VerificationCodeDTO;
import com.msb.internalcommon.response.NumberCodeResponse;
import com.msb.internalcommon.response.TokenResponse;
import com.msb.internalcommon.util.JwtUtils;
import com.msb.internalcommon.util.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {
    @Autowired
    private ServiceVerificationcodeClient serviceVerificationcodeClient;

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 验证码key前缀
    private String verificationCodePrefix = "verification-code-";

    // token key前缀
    private String tokenPrefix = "token-";

    /**
     * 生成验证码
     *
     * @param phoneNumber
     * @return
     */
    public ResponseResult generateVerificationCode(String phoneNumber) {
        // 调用验证码服务，获取验证码
        System.out.println("调用验证码服务，为手机号" + phoneNumber + "发送验证码");
        ResponseResult<NumberCodeResponse> numberCodeResponse =
                serviceVerificationcodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();
        System.out.println("获取到的验证码为：" + numberCode);

        // 存入redis
        System.out.println("将手机号和验证码存入redis");
        // key, value, 过期时间
        String key = RedisPrefixUtils.generateKeyByPhone(phoneNumber,
                IdentityConstants.PASSENGER_IDENTITY);
        // 存入redis
        stringRedisTemplate.opsForValue().set(key, numberCode + "", 2,
                TimeUnit.MINUTES);
        // 通过电信服务商，发送验证码到手机，这里不实现
        return ResponseResult.success(numberCode);
    }

    /**
     * 校验验证码
     *
     * @param passengerPhone
     * @param verificationCode
     * @return
     */
    public ResponseResult checkCode(String passengerPhone, String verificationCode) throws UnsupportedEncodingException {
        // 根据手机号，去redis读取验证码
        System.out.println("根据手机号，去redis读取验证码");
        // 生成key
        String key = RedisPrefixUtils.generateKeyByPhone(passengerPhone,
                IdentityConstants.PASSENGER_IDENTITY);
        // 根据key获取value
        String codeInRedis = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis中的验证码为：" + codeInRedis);

        // 校验验证码
        System.out.println("校验验证码");
        // 判断验证码是否为空
        if (StringUtils.isBlank(codeInRedis + "") || codeInRedis == null) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),
                    CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        // 判断验证码是否正确
        if (!codeInRedis.equals(verificationCode)) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),
                    CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }

        // 判断原来是否有用户，并进行对应处理
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
        verificationCodeDTO.setPassengerPhone(passengerPhone);
        servicePassengerUserClient.loginOrRegister(verificationCodeDTO);

        // 颁发令牌，不应该用魔法值，用常量
        String accessToken = JwtUtils.generateToken(passengerPhone,
                IdentityConstants.PASSENGER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generateToken(passengerPhone,
                IdentityConstants.PASSENGER_IDENTITY, TokenConstants.REFRESH_TOKEN_TYPE);

        // 将token存到redis中
        String accessTokenKey =
                RedisPrefixUtils.generateTokenKey(passengerPhone,
                        IdentityConstants.PASSENGER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken,
         30, TimeUnit.DAYS);

        String refreshTokenKey =
                RedisPrefixUtils.generateTokenKey(passengerPhone,
                        IdentityConstants.PASSENGER_IDENTITY, TokenConstants.REFRESH_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31, TimeUnit.DAYS);

        // 响应
        TokenResponse tokenResponse = new TokenResponse();
        //tokenResponse.setToken(token);
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success(tokenResponse);
    }
}
