package com.vornicu.device_service;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceRequest {


    @NotBlank
    private String deviceId;
    @NotBlank
    private String name;
    @NotBlank
    private String type;
}
