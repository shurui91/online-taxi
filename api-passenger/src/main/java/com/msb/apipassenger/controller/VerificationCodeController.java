package com.msb.apipassenger.controller;

import com.msb.internalcommon.request.VerificationCodeDTO;
import com.msb.apipassenger.service.VerificationCodeService;
import com.msb.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
public class VerificationCodeController {
    @Autowired
    private VerificationCodeService verificationCodeService;

    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("passengerPhone is: " + passengerPhone);
        return verificationCodeService.generateVerificationCode(passengerPhone);
    }

    @PostMapping("/verification-code-check")
    public ResponseResult verificationCodeCheck(@RequestBody VerificationCodeDTO verificationCodeDTO) throws UnsupportedEncodingException {
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        String verificationCode = verificationCodeDTO.getVerificationCode();
        System.out.println("passengerPhone is: " + passengerPhone);
        System.out.println("code is: " + verificationCode);
        return verificationCodeService.checkCode(passengerPhone,
                verificationCode);
    }
}
