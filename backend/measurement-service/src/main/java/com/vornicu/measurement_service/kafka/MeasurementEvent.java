package com.vornicu.measurement_service.kafka;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeasurementEvent {
    private Integer deviceId;
    private Double value;
    private String ownerEmail;
    private Double temperature;
    private String timestamp;
}
