package com.msb.apiBoss.service;

import com.msb.apiBoss.remote.ServiceDriverUserClient;
import com.msb.internalcommon.dto.DriverUser;
import com.msb.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverUserService {
    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult addDriverUser(DriverUser driverUser) {
        return serviceDriverUserClient.addDriverUser(driverUser);
    }
}
