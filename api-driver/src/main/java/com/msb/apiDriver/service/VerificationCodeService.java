package com.msb.apiDriver.service;

import com.msb.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {
    public ResponseResult checkVerificationCode(String driverPhone) {
        // 查询service-driver-user，该手机号的司机是否存在

        // 获取验证码

        // 调用第三方发送验证码

        // 保存验证码到redis
        return ResponseResult.success("");
    }
}
