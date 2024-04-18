package com.msb.apipassenger.service;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.response.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ForecastPriceService {
    /**
     * 根据出发地和目的地经纬度 计算预估价格
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult forcastPrice(String depLongitude,
                                       String depLatitude,
                                       String destLongitude,
                                       String destLatitude) {
        log.info("出发地经度: " + depLongitude);
        log.info("出发地纬度: " + depLatitude);
        log.info("目的地经度: " + destLongitude);
        log.info("目的地纬度: " + destLatitude);
        log.info("开始计算预估价格...");
        // TODO: 根据出发地和目的地经纬度 计算预估价格
        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(12.34);
        return ResponseResult.success(forecastPriceResponse);
    }
}
