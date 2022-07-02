package io.okkio.domain;

import io.okkio.common.AbstractAuditingEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RECEIPT")
public class Receipt extends AbstractAuditingEntity implements Serializable  {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "DETAIL")
    private String detail;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "SHIPMENT_ID")
    private Long shipmentId;

    @Column(name = "PAYMENT_ID")
    private Long paymentId;
}
