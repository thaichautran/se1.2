package com.memorise.memorise_backend.dto;

public class OtpDTO {
    private String otp;

    public OtpDTO() {
    }

    public OtpDTO(String otp) {
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
