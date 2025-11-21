package com.vornicu.measurement_service.measurement;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService service;


    @PostMapping
    public ResponseEntity<MeasurementResponse> add(@AuthenticationPrincipal String email, @RequestBody MeasurementDTO request){
        return ResponseEntity.ok(service.addMeasurement(email,request));
    }
}
