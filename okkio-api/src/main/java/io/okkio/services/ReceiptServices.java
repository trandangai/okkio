package io.okkio.services;

import io.okkio.domain.Receipt;
import io.okkio.domain.Shipment;
import io.okkio.dto.request.RequestShipmentDto;

import java.util.List;

public interface ReceiptServices {
    List<Receipt> getAllReceipt();
    List<Receipt> getReceiptByStatus(String status);
    Receipt addReceipt(Receipt receipt);
    Receipt getReceiptById(Long id);
    boolean deleteReceiptById(Long id);
    boolean update(Long id, Long okkioStatusId, String updatedBy);
}
