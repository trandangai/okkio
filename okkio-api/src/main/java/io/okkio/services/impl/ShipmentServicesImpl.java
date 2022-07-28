package io.okkio.services.impl;

import io.okkio.common.Constants;
import io.okkio.domain.Shipment;
import io.okkio.dto.request.RequestShipmentDto;
import io.okkio.mapper.ShipmentMapper;
import io.okkio.mybatis.ShipmentMybatis;
import io.okkio.repository.ShipmentRepository;
import io.okkio.services.ShipmentServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * ShipmentServicesImpl
 */
@Slf4j
@Service
public class ShipmentServicesImpl extends BaseServiceImpl<Shipment, Long> implements ShipmentServices {

    public ShipmentServicesImpl(JpaRepository<Shipment, Long> jpaRepository) {
        super(jpaRepository);
    }

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ShipmentMapper shipmentMapper;

    @Autowired
    private ShipmentMybatis shipmentMybatis;

    @Override
    public List<Shipment> getAllShipment() {
        return super.findAll();
    }

    @Override
    public List<Shipment> getShipmentByStatus(String status) {
        return shipmentRepository.findShipmentByStatus(status);
    }

    @Override
    public Shipment addShipment(RequestShipmentDto dto) {
        Shipment shipment = shipmentMapper.toEntity(dto);
        shipment.setTrackingNumber("SM" + UUID.randomUUID().toString());
        return save(shipment);
    }

    @Override
    public Shipment getShipmentById(Long id) {
        return shipmentRepository.findShipmentById(id);
    }

    @Override
    public boolean deleteShipmentById(Long id) {
        int updated = shipmentRepository.updateStatusShipment(id, Constants.DEACTIVATED_STATUS);
        if (updated < 0) {
            log.warn("Can't delete shipment with id: " + id);
            return false;
        }
        return true;
    }

    @Override
    public boolean update(RequestShipmentDto dto, String updatedBy) {
        log.info("Payload Request shipment: " + dto.toString());
        int updated = shipmentMybatis.updateShipmentByIds(dto.getId(), dto.getStatus(), dto.getDetail(), dto.getType(),
                        dto.getReceiptId(), dto.getOrderId(), updatedBy);
        if (updated > 0) {
            log.warn("ShipmentServicesImpl - Delete success with id: " + dto.getId());
            return true;
        }
        log.warn("ShipmentServicesImpl - Delete failed with id: " + dto.getId());
        return false;
    }
}
