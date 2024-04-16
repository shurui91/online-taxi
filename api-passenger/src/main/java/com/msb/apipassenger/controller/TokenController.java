package com.msb.apipassenger.controller;

import com.msb.apipassenger.service.TokenService;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @PostMapping("/token-refresh")
    public ResponseResult refreshToken(@RequestBody TokenResponse tokenResponse) throws UnsupportedEncodingException {
        String refreshtokenSrc = tokenResponse.getRefreshToken();
        System.out.println("refreshTokenSrc is: " + refreshtokenSrc);
        return tokenService.refreshToken(refreshtokenSrc);
    }
}
