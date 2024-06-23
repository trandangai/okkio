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
@Table(name = "SHOPPING_CART")
public class ShoppingCart extends AbstractAuditingEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PRODUCT_DETAIL_ID")
    private Long productDetailId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "GRIND")
    private String grind;

    @Column(name = "SIZE")
    private String size;

    @Column(name = "TRANSACTION")
    private String transaction;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "ORDER_ID")
    private Long orderId;
}
