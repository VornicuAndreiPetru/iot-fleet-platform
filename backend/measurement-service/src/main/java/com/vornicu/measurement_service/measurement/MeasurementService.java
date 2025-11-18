package com.vornicu.measurement_service.measurement;


import com.vornicu.measurement_service.kafka.MeasurementEvent;
import com.vornicu.measurement_service.kafka.MeasurementProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MeasurementService {

    private final MeasurementRepository repository;
    private final MeasurementProducer producer;


    public MeasurementResponse addMeasurement(MeasurementDTO request){
        Measurement measurement = Measurement.builder()
                .deviceId(request.getDeviceId())
                .value(request.getValue())
                .timestamp(LocalDateTime.now())
                .build();

        Measurement saved = repository.save(measurement);

        producer.sendMeasurement(
                MeasurementEvent.builder()
                        .deviceId(saved.getDeviceId())
                        .value(saved.getValue())
                        .build()
        );

        return MeasurementResponse.builder()
                .id(saved.getId())
                .deviceId(saved.getDeviceId())
                .value(saved.getValue())
                .timestamp(saved.getTimestamp())
                .build();
    }
}
