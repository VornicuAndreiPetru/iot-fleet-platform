package com.vornicu.measurement_service.measurement;


import lombok.Data;

@Data
public class MeasurementDTO {
    private Integer deviceId;
    private Double value;
    private Double temperature;
}
