package com.msb.apiDriver.service;

import com.msb.apiDriver.remote.ServiceDriverUserClient;
import com.msb.apiDriver.remote.ServiceVerificationcodeClient;
import com.msb.internalcommon.constant.CommonStatusEnum;
import com.msb.internalcommon.constant.DriverCarConstants;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.response.DriverUserExistsResponse;
import com.msb.internalcommon.response.NumberCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VerificationCodeService {
    @Autowired
    ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    ServiceVerificationcodeClient serviceVerificationcodeClient;

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
        return ResponseResult.success("");
    }
}
