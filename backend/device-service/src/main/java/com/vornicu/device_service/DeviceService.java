package com.vornicu.device_service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository repository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public Device registerDevice(Device device) {
        Device saved = repository.save(device);
        kafkaTemplate.send("devnice-events", saved);

        return saved;
    }

    public List<Device> getDevicesByOwner(Integer userId) {
        return repository.findByOwnerId(userId);
    }
}
