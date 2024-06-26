package com.msb.servicemap.service;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.response.DirectionResponse;
import com.msb.servicemap.remote.MapDirectionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
//public class GDDirectionService {
////    @Autowired
////    private MapDirectionClient mapDirectionClient;
//
//    /**
//     * 根据起点和终点经纬度，获取距离和时长，高德地图API
//     * @param depLongitude
//     * @param depLatitude
//     * @param destLongitude
//     * @param destLatitude
//     * @return
//     */
//    public ResponseResult driving(String depLongitude,
//                                  String depLatitude,
//                                  String destLongitude,
//                                  String destLatitude) {
//        mapDirectionClient.directionByGD(depLongitude, depLatitude, destLongitude, destLatitude);
//        DirectionResponse directionResponse = new DirectionResponse();
//        directionResponse.setDistance(1111.0);
//        directionResponse.setDuration(22);
//        return ResponseResult.success(directionResponse);
//    }
//}
