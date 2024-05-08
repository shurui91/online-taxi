package com.msb.internalcommon.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author baomidou
 * @since 2024-05-08
 */
@Data
public class DriverUserWorkStatus {
    private Long id;
    private Long driverId;
    private Integer workStatus;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}
