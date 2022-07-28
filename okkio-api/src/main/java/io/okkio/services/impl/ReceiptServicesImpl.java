package io.okkio.services.impl;

import io.okkio.domain.Receipt;
import io.okkio.dto.request.RequestShipmentDto;
import io.okkio.repository.ReceiptRepository;
import io.okkio.services.ReceiptServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ReceiptServicesImpl
 */
@Slf4j
@Service
public class ReceiptServicesImpl extends BaseServiceImpl<Receipt, Long> implements ReceiptServices {

    public ReceiptServicesImpl(JpaRepository<Receipt, Long> jpaRepository) {
        super(jpaRepository);
    }

    @Autowired
    private ReceiptRepository receiptRepository;

    @Override
    public List<Receipt> getAllReceipt() {
        return null;
    }

    @Override
    public List<Receipt> getReceiptByStatus(String status) {
        return null;
    }

    @Override
    public Receipt addReceipt(Receipt receipt) {
        return super.save(receipt);
    }

    @Override
    public Receipt getReceiptById(Long id) {
        return receiptRepository.findReceiptById(id);
    }

    @Override
    public boolean deleteReceiptById(Long id) {
        return false;
    }

    @Override
    public boolean update(Long id, Long okkioStatusId, String updatedBy) {
        int updated = receiptRepository.updateStatusReceipt(id, okkioStatusId, updatedBy);
        if (updated > 0) {
            log.warn("ReceiptServicesImpl - Updated with receipt id: " + id + " and status: " + okkioStatusId);
            return true;
        }
        log.warn("ReceiptServicesImpl - Failed update with receipt id: " + id + " and status: " + okkioStatusId);
        return false;
    }
}
