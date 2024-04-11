package com.msb.apipassenger.service;

import com.msb.apipassenger.remote.ServiceVerificationcodeClient;
import com.msb.internalcommon.constant.CommonStatusEnum;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.response.NumberCodeResponse;
import com.msb.internalcommon.response.TokenResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {
    @Autowired
    private ServiceVerificationcodeClient serviceVerificationcodeClient;

    @Resource
    private RedisTemplate redisTemplate;

    private String verificationCodePrefix = "passenger-verification-code-";

    /**
     * 生成验证码
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
        String key = generateKeyByPhone(phoneNumber);
        // 存入redis
        redisTemplate.opsForValue().set(key, numberCode, 2, TimeUnit.MINUTES);
        // 通过电信服务商，发送验证码到手机，这里不实现
        return ResponseResult.success();
    }

    /**
     * 根据手机号生成key
     * @param phoneNumber
     * @return
     */
    private String generateKeyByPhone(String phoneNumber) {
        return verificationCodePrefix + phoneNumber;
    }

    /**
     * 校验验证码
     * @param passengerPhone
     * @param verificationCode
     * @return
     */
    public ResponseResult checkCode(String passengerPhone, String verificationCode) {
        // 根据手机号，去redis读取验证码
        System.out.println("根据手机号，去redis读取验证码");
        // 生成key
        String key = generateKeyByPhone(passengerPhone);
        // 根据key获取value
        Integer codeInRedis = (Integer) redisTemplate.opsForValue().get(key);
        System.out.println("redis中的验证码为：" + codeInRedis);

        // 校验验证码
        // 判断验证码是否为空
        if (StringUtils.isBlank(codeInRedis + "") || codeInRedis == null) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),
                    CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        // 判断验证码是否正确
        if (!codeInRedis.equals(Integer.parseInt(verificationCode))) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),
                    CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        System.out.println("校验验证码");
        // 判断原来是否有用户，并进行对应处理
        System.out.println("判断原来是否有用户，并进行对应处理");
        // 颁发令牌
        System.out.println("颁发令牌");
        // 响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken("token value");
        return ResponseResult.success(tokenResponse);
    }
}
