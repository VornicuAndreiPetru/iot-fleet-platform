package com.vornicu.measurement_service.kafka;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeasurementProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMeasurement(MeasurementEvent event){
        kafkaTemplate.send("measurements", event);
    }
}
