package com.vornicu.measurement_service.measurement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {

    List<Measurement> findByDeviceId(Integer deviceId);
}
