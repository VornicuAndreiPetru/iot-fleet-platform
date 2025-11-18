package com.vornicu.measurement_service.measurement;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MeasurementResponse {
    private Integer id;
    private Integer deviceId;
    private Double value;
    private LocalDateTime timestamp;
}
