package com.msb.apipassenger.service;

import com.msb.apipassenger.remote.ServicePassengerUserClient;
import com.msb.internalcommon.dto.PassengerUser;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.dto.TokenResult;
import com.msb.internalcommon.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    @Autowired
    ServicePassengerUserClient servicePassengerUserClient;

    public ResponseResult getUserByAccessToken(String accessToken) {
        log.info("accessToken: {}", accessToken);
        // 解析accessToken，拿到手机号
        TokenResult tokenResult = JwtUtils.checkToken(accessToken);
        String phone = tokenResult.getPhone();
        log.info("phone: {}", phone);

        // 根据手机号查询用户信息
        return servicePassengerUserClient.getUserByPhone(phone);
        //return ResponseResult.success(userByPhone);
    }
}
