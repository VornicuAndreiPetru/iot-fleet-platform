package com.vornicu.device_service;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceService deviceService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<Device> registerDevice(@RequestBody Device device, HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        Integer userId = jwtUtil.extractUserId(token);

        device.setOwnerId(userId);
        Device saved = deviceService.registerDevice(device);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Device>> getMyDevices(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        Integer userId = jwtUtil.extractUserId(token);
        return ResponseEntity.ok(deviceService.getDevicesByOwner(userId));
    }
}
