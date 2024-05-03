package com.msb.serviceDriverUser.service;

import com.msb.internalcommon.dto.Car;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.serviceDriverUser.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CarService {
    @Autowired
    private CarMapper carMapper;

    public ResponseResult addCar(Car car) {
        LocalDateTime now = LocalDateTime.now();
        car.setGmtCreated(now);
        car.setGmtModified(now);
        carMapper.insert(car);
        return ResponseResult.success();
    }
}
