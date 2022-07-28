package io.okkio.services;

import io.okkio.domain.Shipment;
import io.okkio.dto.request.RequestShipmentDto;

import java.util.List;

public interface ShipmentServices {
    List<Shipment> getAllShipment();
    List<Shipment> getShipmentByStatus(String status);
    Shipment addShipment(RequestShipmentDto dto);
    Shipment getShipmentById(Long id);
    boolean deleteShipmentById(Long id);
    boolean update(RequestShipmentDto dto, String updatedBy);
}
