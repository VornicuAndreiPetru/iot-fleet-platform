package com.vornicu.device_service;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name= "devices")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String deviceId;
    private String name;
    private String type;
    private String status;
    private LocalDateTime registeredAt;
    private String ownerEmail;
}
