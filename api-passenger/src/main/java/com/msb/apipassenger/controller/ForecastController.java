package com.msb.apipassenger.controller;

import com.msb.apipassenger.service.ForecastPriceService;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.GDForecastPriceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForecastController {
    @Autowired
    private ForecastPriceService forecastPriceService;

    @PostMapping("/forecast-price")
    public ResponseResult forecastPrice(@RequestBody GDForecastPriceDTO forecastPriceDTO) {
        String startAddress = forecastPriceDTO.getStartAddress();
        String endAddress = forecastPriceDTO.getEndAddress();
        return forecastPriceService.forecastPrice(startAddress, endAddress);
    }
}
