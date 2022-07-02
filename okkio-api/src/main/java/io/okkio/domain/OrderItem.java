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
@Table(name = "ORDER_ITEM")
public class OrderItem extends AbstractAuditingEntity implements Serializable  {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CODE")
    private String code;

    @Column(name = "ORDER_ITEM_STATUS_ID")
    private int orderItemStatusId;

    @Column(name = "SHIPMENT_ITEM_ID")
    private Long shipmentItemId;

    @Column(name = "PRODUCT_DETAIL_ID")
    private Long productDetailId;

    @Column(name = "ORDER_ID")
    private Long orderItemId;
}
