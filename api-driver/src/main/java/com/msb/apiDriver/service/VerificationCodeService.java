package com.msb.apiDriver.service;

import com.msb.apiDriver.remote.ServiceDriverUserClient;
import com.msb.apiDriver.remote.ServiceVerificationcodeClient;
import com.msb.internalcommon.constant.CommonStatusEnum;
import com.msb.internalcommon.constant.DriverCarConstants;
import com.msb.internalcommon.constant.IdentityConstants;
import com.msb.internalcommon.constant.TokenConstants;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.VerificationCodeDTO;
import com.msb.internalcommon.response.DriverUserExistsResponse;
import com.msb.internalcommon.response.NumberCodeResponse;
import com.msb.internalcommon.response.TokenResponse;
import com.msb.internalcommon.util.JwtUtils;
import com.msb.internalcommon.util.RedisPrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class VerificationCodeService {
    @Autowired
    ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    ServiceVerificationcodeClient serviceVerificationcodeClient;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public ResponseResult checkVerificationCode(String driverPhone) {
        // 查询service-driver-user，该手机号的司机是否存在
        ResponseResult<DriverUserExistsResponse> driverUserExistsResponseResponseResult = serviceDriverUserClient.checkDriver(driverPhone);
        DriverUserExistsResponse data = driverUserExistsResponseResponseResult.getData();
        int exists = data.getExists();
        if (exists == DriverCarConstants.DRIVER_NOT_EXISTS) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXISTS.getCode(), CommonStatusEnum.DRIVER_NOT_EXISTS.getValue());
        }
        log.info(driverPhone + " 的司机存在");
        // 获取验证码
        ResponseResult<NumberCodeResponse> numberCodeResult =
                serviceVerificationcodeClient.getNumberCode(6);
        NumberCodeResponse numberCodeResponse = numberCodeResult.getData();
        int numberCode = numberCodeResponse.getNumberCode();
        log.info("验证码为：" + numberCode);

        // 调用第三方发送验证码

        // 保存验证码到redis
        // 1. 生成key, 2. 存入value
        String key = RedisPrefixUtils.generateKeyByPhone(driverPhone,
                IdentityConstants.DRIVER_IDENTITY);
        stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);
        return ResponseResult.success(numberCode);
    }

    /**
     * 校验验证码
     *
     * @param passengerPhone
     * @param verificationCode
     * @return
     */
    public ResponseResult checkCode(String driverPhone, String verificationCode) throws UnsupportedEncodingException {
        // 根据手机号，去redis读取验证码
        System.out.println("根据手机号，去redis读取司机的验证码");
        // 生成key
        String key = RedisPrefixUtils.generateKeyByPhone(driverPhone,
                IdentityConstants.DRIVER_IDENTITY);
        // 根据key获取value
        String codeInRedis = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis中司机的验证码为：" + codeInRedis);

        // 校验验证码
        System.out.println("校验司机验证码");
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

        // 颁发令牌，不应该用魔法值，用常量
        String accessToken = JwtUtils.generateToken(driverPhone,
                IdentityConstants.DRIVER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generateToken(driverPhone,
                IdentityConstants.DRIVER_IDENTITY, TokenConstants.REFRESH_TOKEN_TYPE);

        // 将token存到redis中
        String accessTokenKey =
                RedisPrefixUtils.generateTokenKey(driverPhone,
                        IdentityConstants.DRIVER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken,
                30, TimeUnit.DAYS);

        String refreshTokenKey =
                RedisPrefixUtils.generateTokenKey(driverPhone,
                        IdentityConstants.DRIVER_IDENTITY, TokenConstants.REFRESH_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31, TimeUnit.DAYS);

        // 响应
        TokenResponse tokenResponse = new TokenResponse();
        //tokenResponse.setToken(token);
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success(tokenResponse);
    }
}
