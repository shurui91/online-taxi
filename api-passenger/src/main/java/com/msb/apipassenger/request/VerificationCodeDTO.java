package com.msb.apipassenger.request;

public class VerificationCodeDTO {
    private String passengerPhone;

    public VerificationCodeDTO() {
    }

    public VerificationCodeDTO(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }
}
