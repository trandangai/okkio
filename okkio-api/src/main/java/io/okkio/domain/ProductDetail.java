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
@Table(name = "PRODUCT_DETAIL")
public class ProductDetail extends AbstractAuditingEntity implements Serializable  {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "GRIND")
    private String grind;

    @Column(name = "SIZE")
    private String size;

    @Column(name = "SUBSCRIPTION")
    private String subscription;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "ROAST_LEVEL")
    private int roastLevel;

    @Column(name = "TASTING_NOTES")
    private String tastingNotes;

    @Column(name = "READY_TO_DRINK")
    private String readyToDrink;

    @Column(name = "SUITABLE_FOR")
    private String suitableFor;

    @Column(name = "SHIPPING_DELIVERY")
    private String shippingDelivery;

    @Column(name = "HEADER_IMAGES")
    private String headerImages;

    @Column(name = "FOOTER_IMAGES")
    private String footerImages;

    @Column(name = "SUGGESTION")
    private String suggestion;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "ORDER_ITEM_ID")
    private Long orderItemId;
}
