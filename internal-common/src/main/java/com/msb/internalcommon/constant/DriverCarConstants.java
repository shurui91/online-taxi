package com.msb.internalcommon.constant;

public class DriverCarConstants {
    /**
     * 司机车辆关系状态：绑定
     */
    public static Integer DRIVER_CAR_BIND = 1;
    /**
     * 司机车辆关系状态：解绑
     */
    public static Integer DRIVER_CAR_UNBIND = 2;
    /**
     * 司机状态：有效
     */
    public static Integer DRIVER_STATE_VALID = 1;
    /**
     * 司机状态：无效
     */
    public static Integer DRIVER_STATE_INVALID = 0;

    /**
     * 司机状态：存在1，不存在0
     */
    public static int DRIVER_NOT_EXISTS = 0;
    public static int DRIVER_EXISTS = 1;
    /**
     * 司机工作状态
     * 0: 收车
     * 1: 出车
     * 2: 暂停
     */
    public static int DRIVER_WORK_STATUS_START = 1;
    public static int DRIVER_WORK_STATUS_STOP = 0;
    public static int DRIVER_WORK_STATUS_SUSPEND = 2;
}
