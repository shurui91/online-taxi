package com.msb.internalcommon.constant;

public enum CommonStatusEnum {
    /**
     * 验证码错误提示：1000-1099
     */
    VERIFICATION_CODE_ERROR(1099, "验证码不正确"),

    /**
     * 验证码过期提示：1100-1199
     */
    TOKEN_ERROR(1199, "token错误"),
    USER_NOT_EXISTS(1200, "当前用户不存在"),
    SUCCESS(1, "success"),
    FAIL(0, "fail");
    int code;
    String value;

    CommonStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
