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
 * @since 2024-05-02
 */
@Data
public class DriverCarBindingRelationship implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long driverId;
    private Long carId;
    private Integer bindState;
    private LocalDateTime bindingTime;
    private LocalDateTime unBindingTime;
}
