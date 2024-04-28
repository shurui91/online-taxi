package com.msb.apipassenger.service;

import com.msb.apipassenger.remote.ServicePriceClient;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.GoogleForecastPriceDTO;
import com.msb.internalcommon.response.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ForecastPriceService {
    @Autowired
    ServicePriceClient servicePriceClient;

    /**
     * 根据出发地和目的地经纬度 计算预估价格
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult forecastPrice(String startAddress, String endAddress) {
        log.info("startAddress is" + startAddress);
        log.info("endAddress is" + endAddress);
        log.info("调用计价服务，计算价格");
        GoogleForecastPriceDTO forecastPriceDTO = new GoogleForecastPriceDTO();
        forecastPriceDTO.setStartAddress(startAddress);
        forecastPriceDTO.setEndAddress(endAddress);
        ResponseResult<ForecastPriceResponse> forecast = servicePriceClient.forecast(forecastPriceDTO);
        double price = forecast.getData().getPrice();

        // TODO: 根据出发地和目的地 计算预估价格
        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(price);
        return ResponseResult.success(forecastPriceResponse);
    }
}
