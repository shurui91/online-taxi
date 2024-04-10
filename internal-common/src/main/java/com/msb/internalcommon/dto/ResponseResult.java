package com.msb.internalcommon.dto;

import com.msb.internalcommon.constant.CommonStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseResult<T> {
    private int code;
    private String message;
    private T data;

    /**
     * 成功
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<T>().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getValue()).setData(data);
    }

    /**
     * 失败: 自定义错误码和异常信息
     * @param data
     * @return
     * @param <T>
     */
    public static ResponseResult fail(int code, String message) {
        return new ResponseResult().setCode(code).setMessage(message);
    }

    /**
     * 失败: 自定义错误码、异常信息和返回数据
     * @param code
     * @param message
     * @param data
     * @return
     */
    public static ResponseResult fail(int code, String message, String data) {
        return new ResponseResult().setCode(code).setMessage(message).setData(data);
    }

    /**
     * 失败: 统一的失败
     * @param commonStatusEnum
     * @return
     */
    public static<T> ResponseResult fail(T data) {
        return new ResponseResult().setData(data);
    }
}
