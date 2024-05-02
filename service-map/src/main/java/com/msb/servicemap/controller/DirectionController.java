package com.msb.servicemap.controller;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.GoogleForecastPriceDTO;
import com.msb.servicemap.service.GoogleDirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/direction")
public class DirectionController {
    @Autowired
    private GoogleDirectionService directionService;
    @GetMapping ("/googledriving")
    public ResponseResult driving(@RequestBody GoogleForecastPriceDTO forecastPriceDTO) {
        String startAddress = forecastPriceDTO.getStartAddress();
        String endAddress = forecastPriceDTO.getEndAddress();
        return directionService.driving(startAddress, endAddress);
    }
}
