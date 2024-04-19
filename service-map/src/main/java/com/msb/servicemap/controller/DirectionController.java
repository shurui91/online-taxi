package com.msb.servicemap.controller;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.ForecastPriceDTO;
import com.msb.servicemap.service.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/direction")
public class DirectionController {
    @Autowired
    private DirectionService directionService;

    @GetMapping("/driving")
    public ResponseResult driving(@RequestBody ForecastPriceDTO forecastPriceDTO) {
        String depLongitude = forecastPriceDTO.getDepLatitude();
        String depLatitude = forecastPriceDTO.getDepLongitude();
        String destLongitude = forecastPriceDTO.getDestLongitude();
        String destLatitude = forecastPriceDTO.getDestLatitude();
        return directionService.driving(depLongitude, depLatitude, destLongitude, destLatitude);
    }
}
