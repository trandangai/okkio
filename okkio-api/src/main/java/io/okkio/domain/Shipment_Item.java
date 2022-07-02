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
@Table(name = "SHIPMENT_ITEM")
public class Shipment_Item extends AbstractAuditingEntity implements Serializable  {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "RECEIPT_ID")
    private Long receiptId;

    @Column(name = "ORDER_ITEM_ID")
    private Long orderItemId;
}
