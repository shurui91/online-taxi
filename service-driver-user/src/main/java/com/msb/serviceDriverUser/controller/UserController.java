package com.msb.serviceDriverUser.controller;

import com.msb.internalcommon.constant.DriverCarConstants;
import com.msb.internalcommon.dto.DriverUser;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.response.DriverUserExistsResponse;
import com.msb.serviceDriverUser.service.DriverUserService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private DriverUserService driverUserService;

    /**
     * 新增司机
     * @param driverUser
     * @return
     */
    @PostMapping("/user")
    public ResponseResult addUser(@RequestBody DriverUser driverUser) {
        log.info(JSONObject.fromObject(driverUser).toString());
        return driverUserService.addDriverUser(driverUser);
    }

    /**
     * 修改司机
     * @param driverUser
     * @return
     */
    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser) {
        log.info(JSONObject.fromObject(driverUser).toString());
        return driverUserService.updateDriverUser(driverUser);
    }

    /**
     * 根据条件查询司机
     * 如果需要按照司机的多个条件做查询，此处用 /user
     * @param driverUser
     * @return
     */
    @GetMapping("/check-driver/{driverPhone}")
    public ResponseResult getUser(@PathVariable("driverPhone") String driverPhone) {
        ResponseResult<DriverUser> driverUserByPhone = driverUserService.getDriverUserByPhone(driverPhone);
        DriverUser driverPhoneDb = driverUserByPhone.getData();
        DriverUserExistsResponse response = new DriverUserExistsResponse();
        int ifExists = DriverCarConstants.DRIVER_EXISTS;
        if (driverPhoneDb == null) {
            ifExists = DriverCarConstants.DRIVER_NOT_EXISTS;
            response.setDriverPhone(driverPhone);
        } else {
            response.setDriverPhone(driverPhoneDb.getDriverPhone() == null ? "" : driverPhoneDb.getDriverPhone());
        }
        response.setExists(ifExists);
        return ResponseResult.success(response);
    }
}
