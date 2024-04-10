package com.msb.serviceverificationcodeapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServerVerificationcodeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerVerificationcodeApplication.class, args);
    }
}
