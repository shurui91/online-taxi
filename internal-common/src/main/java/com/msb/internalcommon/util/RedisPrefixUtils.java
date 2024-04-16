package com.msb.internalcommon.util;

public class RedisPrefixUtils {
    // 验证码key前缀
    public static String verificationCodePrefix = "passenger-verification-code-";

    // token key前缀
    public static String tokenPrefix = "token-";

    /**
     * 根据手机号生成key
     *
     * @param phoneNumber
     * @return
     */
    public static String generateKeyByPhone(String phoneNumber) {
        return verificationCodePrefix + phoneNumber;
    }

    /**
     * 根据手机号和身份生成token key
     *
     * @param phone
     * @param identity
     * @return
     */
    public static String generateTokenKey(String phone, String identity,
                                          String tokenType) {
        return tokenPrefix + phone + "-" + identity + "-" + tokenType;
    }
}
