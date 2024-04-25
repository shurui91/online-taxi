package com.msb.internalcommon.request;

import lombok.Data;

@Data
public class GDForecastPriceDTO {
    private String depLongitude;
    private String depLatitude;
    private String destLongitude;
    private String destLatitude;
}
