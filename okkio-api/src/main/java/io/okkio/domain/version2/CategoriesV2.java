package io.okkio.domain.version2;

import io.okkio.common.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@Entity
@Table(name = "CATEGORIES_V2")
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesV2 extends AbstractAuditingEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PRIORITY")
    private int priority;

    @OneToMany(mappedBy = "categoriesV2")
    Set<ProductV2> productV2s;
}
