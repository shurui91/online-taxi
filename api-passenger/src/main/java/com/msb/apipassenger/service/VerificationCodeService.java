package com.msb.apipassenger.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {
    public String getVerificationCode(String phoneNumber) {
        // 调用验证码服务，获取验证码
        System.out.println("调用验证码服务，为手机号" + phoneNumber + "发送验证码");
        String code = "666666";
        // 存入redis
        System.out.println("将手机号和验证码存入redis");
        // 返回验证码
        JSONObject result = new JSONObject();
        result.put("code", 1);
        result.put("message", "success");
        return result.toString();
    }
}
