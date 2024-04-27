package com.msb.serviceprice.service;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.GoogleForecastPriceDTO;
import com.msb.internalcommon.response.DirectionResponse;
import com.msb.internalcommon.response.ForecastPriceResponse;
import com.msb.serviceprice.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ForecastPriceService {
    @Autowired
    private ServiceMapClient serviceMapClient;

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
        log.info("出发地: " + startAddress);
        log.info("目的地: " + endAddress);
        log.info("调用地图服务，查询距离和时长");
        GoogleForecastPriceDTO forecastPriceDTO = new GoogleForecastPriceDTO();
        forecastPriceDTO.setStartAddress(startAddress);
        forecastPriceDTO.setEndAddress(endAddress);
        ResponseResult<DirectionResponse> direction = serviceMapClient.direction(forecastPriceDTO);
        Double distance = direction.getData().getDistance();
        Integer duration = direction.getData().getDuration();
        log.info("距离 (miles): " + distance + " 时长 (minutes): " + duration);

        log.info("读取计价规则");

        log.info("根据距离和时长，计算价格");

        // TODO: 根据出发地和目的地经纬度 计算预估价格
        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(12.34);
        return ResponseResult.success(forecastPriceResponse);
    }
}
