package com.msb.apipassenger.remote;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.GoogleForecastPriceDTO;
import com.msb.internalcommon.response.ForecastPriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "service-price")
public interface ServicePriceClient {
    @RequestMapping(method = RequestMethod.POST, value = "/forecast-price")
    ResponseResult<ForecastPriceResponse> forecast(@RequestBody GoogleForecastPriceDTO forecastPriceDTO);
}
