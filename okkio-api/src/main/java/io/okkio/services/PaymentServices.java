package io.okkio.services;

import io.okkio.domain.Payment;
import io.okkio.dto.request.RequestPaymentDto;
import io.okkio.dto.request.RequestShipmentDto;

import java.util.List;

public interface PaymentServices {
    List<Payment> getAllShipment();
    List<Payment> getPaymentByStatus(String status);
    Payment addPayment(RequestPaymentDto dto, String email);
    Payment getPaymentById(Long id);
//    boolean deleteShipmentById(Long id);
//    boolean update(RequestShipmentDto dto, String updatedBy);
}
