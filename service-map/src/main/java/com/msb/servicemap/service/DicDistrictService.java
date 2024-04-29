package com.msb.servicemap.service;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.servicemap.remote.MapDicDistrictClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DicDistrictService {
    @Autowired
    private MapDicDistrictClient mapDicDistrictClient;

    public ResponseResult initDicDistrict() {
        String str = mapDicDistrictClient.initDicDistrict();
        System.out.println(str);
        return ResponseResult.success();
    }
}
