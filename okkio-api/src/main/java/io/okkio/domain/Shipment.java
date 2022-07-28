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
@Table(name = "SHIPMENT")
public class Shipment extends AbstractAuditingEntity implements Serializable  {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TRACKING_NUMBER")
    private String trackingNumber;

    @Column(name = "DETAIL")
    private String detail;

    @Column(name = "SHIPPING_TO")
    private String shippingTo;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "RECEIPT_ID")
    private Long receiptId;

    @Column(name = "ORDER_ID")
    private Long orderId;
}
