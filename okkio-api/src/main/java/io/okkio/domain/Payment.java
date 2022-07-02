package io.okkio.domain;

import io.okkio.common.AbstractAuditingEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RECEIPT")
public class Payment extends AbstractAuditingEntity implements Serializable  {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "PAYMENT_METHOD")
    private String paymentMethod;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "RECEIPT_ID")
    private Long receiptId;
}
