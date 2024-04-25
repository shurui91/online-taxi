package com.msb.servicemap.controller;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.GDForecastPriceDTO;
import com.msb.internalcommon.request.GoogleForecastPriceDTO;
import com.msb.servicemap.service.GDDirectionService;
import com.msb.servicemap.service.GoogleDirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/direction")
public class DirectionController {
    @Autowired
    private GoogleDirectionService directionService;

//    @GetMapping("/driving")
//    public ResponseResult driving(@RequestBody GDForecastPriceDTO forecastPriceDTO) {
//        String depLongitude = forecastPriceDTO.getDepLatitude();
//        String depLatitude = forecastPriceDTO.getDepLongitude();
//        String destLongitude = forecastPriceDTO.getDestLongitude();
//        String destLatitude = forecastPriceDTO.getDestLatitude();
//        return directionService.driving(depLongitude, depLatitude, destLongitude, destLatitude);
//    }

    @GetMapping("/googledriving")
    public ResponseResult driving(@RequestBody GoogleForecastPriceDTO forecastPriceDTO) {
        String startAddress = forecastPriceDTO.getStartAddress();
        String endAddress = forecastPriceDTO.getEndAddress();
        return directionService.driving(startAddress, endAddress);
    }
}
