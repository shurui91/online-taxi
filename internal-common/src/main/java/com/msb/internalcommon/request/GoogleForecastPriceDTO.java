package com.msb.internalcommon.request;

import lombok.Data;

@Data
public class GoogleForecastPriceDTO {
    private String startAddress;
    private String endAddress;
}
