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
@Table(name = "ORDER_ITEM")
public class OrderItem extends AbstractAuditingEntity implements Serializable  {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "OKKIO_STATUS_ID")
    private Long okkioStatusId;

    @Column(name = "PRODUCT_DETAIL_ID")
    private Long productDetailId;

    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "SALE_ID")
    private Long saleId;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "GRIND")
    private String grind;

    @Column(name = "SIZE")
    private String size;

    @Column(name = "SUBSCRIPTION")
    private String subscription;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "SALE_PRICE")
    private BigDecimal salePrice;
}