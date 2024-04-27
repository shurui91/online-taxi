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
    public ResponseResult forecastPrice(String startAddress,
                                       String endAddress) {
        log.info("startAddress is" + startAddress);
        log.info("endAddress is" + endAddress);
        log.info("开始计算预估价格...");
        // TODO: 根据出发地和目的地经纬度 计算预估价格
        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(12.34);
        return ResponseResult.success(forecastPriceResponse);
    }
}
