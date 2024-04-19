package com.msb.servicemap.remote;

import com.msb.internalcommon.constant.AmapConfigConstants;
import com.msb.internalcommon.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MapDirectionClient {
    public DirectionResponse direction(String depLongitude,
                                       String depLatitude,
                                       String destLongitude,
                                       String destLatitude) {
        // 组装请求调用api
        // //restapi.amap.com/v3/direction/driving?key=您的key&origin=116.481028,39.989643&destination=116.434446,39.90816&originid=&destinationid=&extensions=base&strategy=0&waypoints=116.357483,39.907234&avoidpolygons=&avoidroad=
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(AmapConfigConstants.DIRECTION_URL);
        urlBuilder.append("?");
        urlBuilder.append("origin=" + depLongitude + "," + depLatitude);
        urlBuilder.append("&");
        urlBuilder.append("destination=" + destLongitude + "," + destLatitude);
        urlBuilder.append("&");
        urlBuilder.append("extensions=base");
        urlBuilder.append("&");
        urlBuilder.append("output=json");
        urlBuilder.append("&");
        urlBuilder.append("key=" + AmapConfigConstants.API_KEY);
        log.info("请求高德地图API：" + urlBuilder.toString());

        // 调用高德接口

        // 解析接口
        return null;
    }
}
