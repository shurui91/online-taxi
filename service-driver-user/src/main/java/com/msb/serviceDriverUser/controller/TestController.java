package com.msb.serviceDriverUser.controller;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.serviceDriverUser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private DriverUserService driverUserService;

    @GetMapping("/test")
    public String test() {
        return "test-service-driver-user";
    }

    @GetMapping("/test-db")
    public ResponseResult testDb() {
        return driverUserService.testGetDriverUser();
    }
}
