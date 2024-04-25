package com.msb.servicemap.remote;

import com.msb.internalcommon.constant.AmapConfigConstants;
import com.msb.internalcommon.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class MapDirectionClient {
    @Autowired
    private RestTemplate restTemplate;

    public DirectionResponse directionByGD(String depLongitude,
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
        ResponseEntity<String> directionEntity =
                restTemplate.getForEntity(urlBuilder.toString(),
                        String.class);
        log.info("高德地图路径规划返回信息：" + directionEntity.getBody());

        // 解析接口
        return null;
    }

    public DirectionResponse directionByGoogle(String startAddress,
                                               String endAddress) {
        // startAddress = startAddress.replace(",", "+");
        // startAddress = startAddress.replace(" ", "%20");
        // endAddress = endAddress.replace(",", "+");
        // endAddress = endAddress.replace(" ", "%20");
        // 组装请求调用api
        // https://maps.googleapis.com/maps/api/directions/json?origin=Cerritos,%20CA&destination=Lakewood,%20CA&key=xxxx
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(AmapConfigConstants.DIRECTION_URL);
        urlBuilder.append("json?");
        urlBuilder.append("origin=" + startAddress);
        urlBuilder.append("&");
        urlBuilder.append("destination=" + endAddress);
        urlBuilder.append("&");
        urlBuilder.append("key=" + AmapConfigConstants.API_KEY);
        //log.info("请求谷歌地图API：" + urlBuilder.toString());

        // 调用谷歌接口
        ResponseEntity<String> directionEntity = restTemplate.getForEntity(urlBuilder.toString(), String.class);
        String directionStr = directionEntity.getBody();
        //log.info("谷歌地图路径规划返回信息：" + directionEntity.getBody());

        // 解析接口
        DirectionResponse directionResponse = parseDirectionEntity(directionStr);
        return directionResponse;
    }

    private DirectionResponse parseDirectionEntity(String directionStr) {
        DirectionResponse directionResponse = null;
        try {
            directionResponse = new DirectionResponse();
            // 最外层
            JSONObject res = JSONObject.fromObject(directionStr);
            if (res.has(AmapConfigConstants.STATUS)) {
                String status = res.getString(AmapConfigConstants.STATUS);
                if (status.equals("OK")) {
                    log.info("OK这里可以进来");
                    if (res.has(AmapConfigConstants.ROUTES)) {
                        log.info("routes这里可以进来");
                        // 只有一种方案所以直接就返回steps
                        JSONObject routeObject = res.getJSONArray(AmapConfigConstants.ROUTES).getJSONObject(0);
                        JSONObject legs = routeObject.getJSONArray(AmapConfigConstants.LEGS).getJSONObject(0);
                        if (legs.has(AmapConfigConstants.DISTANCE)) {
                            JSONObject distanceObject = legs.getJSONObject(AmapConfigConstants.DISTANCE);
                            String distanceText = distanceObject.getString(AmapConfigConstants.TEXT);
                            double distance = Double.parseDouble(distanceText.substring(0, distanceText.indexOf(" ")));
                            directionResponse.setDistance(distance);
                        }
                        if (legs.has(AmapConfigConstants.DURATION)) {
                            JSONObject durationObject = legs.getJSONObject(AmapConfigConstants.DURATION);
                            String durationText = durationObject.getString(AmapConfigConstants.TEXT);
                            int duration = Integer.parseInt(durationText.substring(0, durationText.indexOf(" ")));
                            directionResponse.setDuration(duration);
                        }
                    }
                }
            }
        } catch (Exception e) {

        }
        return directionResponse;
    }
}
