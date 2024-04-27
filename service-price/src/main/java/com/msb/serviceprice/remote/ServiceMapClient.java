package com.msb.serviceprice.remote;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.GoogleForecastPriceDTO;
import com.msb.internalcommon.response.DirectionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "service-map")
public interface ServiceMapClient {
    @RequestMapping(method = RequestMethod.GET, value = "/direction/googledriving")
    ResponseResult<DirectionResponse> direction(@RequestBody GoogleForecastPriceDTO forecastPriceDTO);
}
