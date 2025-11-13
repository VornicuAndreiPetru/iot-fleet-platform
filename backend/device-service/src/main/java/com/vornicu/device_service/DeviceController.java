package com.vornicu.device_service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceRepository deviceRepository;

    @PostMapping
    public ResponseEntity<Device> addDevice(@AuthenticationPrincipal String email,
                                            @RequestBody Device device) {
        device.setOwnerEmail(email);
        Device saved = deviceRepository.save(device);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Device>> getDevices(@AuthenticationPrincipal String email) {
        List<Device> devices = deviceRepository.findAll().stream()
                .filter(d -> d.getOwnerEmail().equals(email))
                .toList();
        return ResponseEntity.ok(devices);
    }
}
