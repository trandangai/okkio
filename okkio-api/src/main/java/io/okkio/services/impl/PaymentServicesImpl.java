package io.okkio.services.impl;

import io.okkio.domain.Payment;
import io.okkio.dto.request.RequestPaymentDto;
import io.okkio.mapper.PaymentMapper;
import io.okkio.repository.PaymentRepository;
import io.okkio.services.PaymentServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PaymentServicesImpl
 */
@Slf4j
@Service
public class PaymentServicesImpl extends BaseServiceImpl<Payment, Long> implements PaymentServices {

    public PaymentServicesImpl(JpaRepository<Payment, Long> jpaRepository) {
        super(jpaRepository);
    }

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public List<Payment> getAllShipment() {
        return paymentRepository.findAll();
    }

    @Override
    public List<Payment> getPaymentByStatus(String status) {
        return paymentRepository.findPaymentByStatus(status);
    }

    @Override
    public Payment addPayment(RequestPaymentDto dto, String email) {
        Payment payment = paymentMapper.toEntity(dto);
        payment.setCreatedBy(email);
        log.info("Payment info: " + dto.toString() + " user: " + email);
        return super.save(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findPaymentById(id);
    }
}
