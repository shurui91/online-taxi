package com.msb.apipassenger.service;

import com.msb.apipassenger.remote.ServiceVerificationcodeClient;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.response.NumberCodeResponse;
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

    public ResponseResult getVerificationCode(String phoneNumber) {
        // 调用验证码服务，获取验证码
        System.out.println("调用验证码服务，为手机号" + phoneNumber + "发送验证码");
        ResponseResult<NumberCodeResponse> numberCodeResponse =
                serviceVerificationcodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();
        System.out.println("获取到的验证码为：" + numberCode);


        // 存入redis
        System.out.println("将手机号和验证码存入redis");
        // key, value, 过期时间
        String key = verificationCodePrefix + phoneNumber;
        // 存入redis
        redisTemplate.opsForValue().set(key, numberCode, 2, TimeUnit.MINUTES);
        // 通过电信服务商，发送验证码到手机，这里不实现
        return ResponseResult.success();
    }
}
