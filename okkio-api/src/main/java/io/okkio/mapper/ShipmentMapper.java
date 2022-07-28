package io.okkio.mapper;

import io.okkio.domain.Product;
import io.okkio.domain.Shipment;
import io.okkio.dto.request.RequestProductDto;
import io.okkio.dto.request.RequestShipmentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ShipmentMapper {
    public abstract Shipment toEntity(RequestShipmentDto dto);
}
