package com.msb.serviceprice.controller;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.GDForecastPriceDTO;
import com.msb.serviceprice.service.ForecastPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForecastPriceController {
    @Autowired
    private ForecastPriceService forecastPriceService;

    @PostMapping("/forecast-price")
    public ResponseResult forecastPrice(@RequestBody GDForecastPriceDTO forecastPriceDTO) {
        String startAddress = forecastPriceDTO.getStartAddress();
        String endAddress = forecastPriceDTO.getEndAddress();
        return forecastPriceService.forecastPrice(startAddress, endAddress);
    }
}
