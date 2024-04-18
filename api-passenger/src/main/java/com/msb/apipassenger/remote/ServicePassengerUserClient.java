package com.msb.apipassenger.remote;

import com.msb.internalcommon.dto.PassengerUser;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.VerificationCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {
    @RequestMapping(method = RequestMethod.POST, value = "/user")
    ResponseResult<PassengerUser> loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO);

    @RequestMapping(method = RequestMethod.GET, value = "/user/{phone}")
    ResponseResult<PassengerUser> getUserByPhone(@PathVariable("phone") String passengerPhone);
}
