package com.msb.serviceverificationcodeapplication.controller;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {
    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") Integer size) {
        // 生成验证码
        double random = (Math.random() * 9 + 1) * (Math.pow(10, size - 1));
        int resInt = (int) random;
        System.out.println(resInt);

        // 返回结果
        NumberCodeResponse response = new NumberCodeResponse();
        response.setNumberCode(resInt);
        return ResponseResult.success(response);
    }

//    public static void main(String[] args) {
//        // 获取随机数
//        double random = (Math.random() * 9 + 1) * (Math.pow(10, 5));
//        int res = (int) random;
//        System.out.println(res);
//    }
}
