package com.msb.serviceDriverUser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.msb.internalcommon.constant.CommonStatusEnum;
import com.msb.internalcommon.constant.DriverCarConstants;
import com.msb.internalcommon.dto.DriverCarBindingRelationship;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.serviceDriverUser.mapper.DriverCarBindingRelationshipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author baomidou
 * @since 2024-05-02
 */
@Service
public class DriverCarBindingRelationshipService {
    @Autowired
    private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship) {
        // 判断，如果车辆和司机已经做过绑定，则不能再次绑定
        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id", driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("car_id", driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state", DriverCarConstants.DRIVER_CAR_BIND);
        Integer i = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if (i > 0) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getCode(), CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getValue());
        }

        // 司机被绑定
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id", driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("bind_state", DriverCarConstants.DRIVER_CAR_BIND);
        i = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if (i > 0) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_BIND_EXISTS.getCode(), CommonStatusEnum.DRIVER_BIND_EXISTS.getValue());
        }

        // 车辆被绑定
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_id", driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state", DriverCarConstants.DRIVER_CAR_BIND);
        i = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if (i > 0) {
            return ResponseResult.fail(CommonStatusEnum.CAR_BIND_EXISTS.getCode(), CommonStatusEnum.CAR_BIND_EXISTS.getValue());
        }

        LocalDateTime now = LocalDateTime.now();
        driverCarBindingRelationship.setBindingTime(now);
        driverCarBindingRelationship.setBindState(DriverCarConstants.DRIVER_CAR_BIND);
        driverCarBindingRelationshipMapper.insert(driverCarBindingRelationship);
        return ResponseResult.success();
    }
}
