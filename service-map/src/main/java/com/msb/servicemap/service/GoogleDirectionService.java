package com.msb.servicemap.service;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.response.DirectionResponse;
import com.msb.servicemap.remote.MapDirectionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoogleDirectionService {
    @Autowired
    private MapDirectionClient mapDirectionClient;

    public ResponseResult driving(String startAddress, String endAddress) {
        DirectionResponse directionResponse = mapDirectionClient.directionByGoogle(startAddress, endAddress);
        return ResponseResult.success(directionResponse);
    }
}
