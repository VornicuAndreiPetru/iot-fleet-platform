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


    public MeasurementResponse addMeasurement(String email,MeasurementDTO request){
        Measurement measurement = Measurement.builder()
                .deviceId(request.getDeviceId())
                .value(request.getValue())
                .temperature(request.getTemperature())
                .ownerEmail(email)
                .timestamp(LocalDateTime.now())
                .build();

        Measurement saved = repository.save(measurement);

        producer.sendMeasurement(
                MeasurementEvent.builder()
                        .deviceId(saved.getDeviceId())
                        .value(saved.getValue())
                        .temperature(saved.getTemperature())
                        .ownerEmail(saved.getOwnerEmail())
                        .timestamp(saved.getTimestamp().toString())
                        .build()
        );

        return MeasurementResponse.builder()
                .id(saved.getId())
                .deviceId(saved.getDeviceId())
                .value(saved.getValue())
                .temperature(saved.getTemperature())
                .timestamp(saved.getTimestamp())
                .build();
    }
}
