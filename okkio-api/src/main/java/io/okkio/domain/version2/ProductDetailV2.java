package io.okkio.domain.version2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.okkio.common.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Filter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCT_DETAIL_V2")
public class ProductDetailV2 extends AbstractAuditingEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SHORT_NAME")
    private String shortName;

    @Column(name = "DESCRIPTION", length = 2555)
    private String description;

    @Column(name = "STATUS")
    private String status;

    // WHOLE BEANS / ESPRESSO MACHINE / POUR OVER / PHIN / OTHERS
    @Column(name = "GRIND")
    private String grind;

    @Column(name = "SIZE")
    private String size;

    // lightest, medium, darknest
    @Column(name = "FLAVOR_NOTE")
    private String flavorNote;

    // 6 levels
    @Column(name = "ROAST_LEVEL")
    private String roastLevel;

    @Column(name = "ALTITUDE")
    private String altitude;

    @Column(name = "VARIETAL")
    private String varietal;

    @Column(name = "PROCESSING")
    private String processing;

    @Column(name = "MATERIAL")
    private int material;

    @ElementCollection
    @CollectionTable(name = "PRODUCT_DETAIL_IMAGE", joinColumns = @JoinColumn(name = "productdetailv2_id"))
    @Column(name = "PRODUCT_IMAGES")
    private List<String> productImages;

    @Column(name = "SUGGESTION_1ST")
    private Long suggestion1stProduct;

    @Column(name = "SUGGESTION_2ND")
    private Long suggestion2ndProduct;

    @Column(name = "slug")
    private String slug;

    @Column(name = "PRICE")
    private BigDecimal price;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @JsonIgnore
    ProductV2 productV2;
}