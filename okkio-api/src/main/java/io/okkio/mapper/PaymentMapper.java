package io.okkio.mapper;

import io.okkio.domain.Payment;
import io.okkio.dto.request.RequestPaymentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PaymentMapper {
    public abstract Payment toEntity(RequestPaymentDto dto);
}
