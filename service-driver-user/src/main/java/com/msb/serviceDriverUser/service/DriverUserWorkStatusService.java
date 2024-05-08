package com.msb.serviceDriverUser.service;

import com.msb.internalcommon.dto.DriverUserWorkStatus;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.serviceDriverUser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2024-05-08
 */
@Service
public class DriverUserWorkStatusService {
    @Autowired
    DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    public ResponseResult changeWorkStatus(Long driverId, Integer workStatus) {
        Map<String, Object> map = new HashMap<>();
        map.put("driver_id", driverId);
        List<DriverUserWorkStatus> list =
                driverUserWorkStatusMapper.selectByMap(map);
        DriverUserWorkStatus driverUserWorkStatus = list.get(0);
        driverUserWorkStatus.setWorkStatus(workStatus);
        driverUserWorkStatusMapper.updateById(driverUserWorkStatus);
        return ResponseResult.success("work status changed successfully!QAQ");
    }
}
