package com.msb.serviceDriverUser.controller;


import com.msb.internalcommon.dto.Car;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.serviceDriverUser.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2024-05-02
 */
@RestController
public class CarController {
    @Autowired
    CarService carService;

    @PostMapping("/car")
    public ResponseResult addCar(@RequestBody Car car) {
        return carService.addCar(car);
    }
}
