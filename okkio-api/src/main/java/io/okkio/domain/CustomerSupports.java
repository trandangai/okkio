package io.okkio.domain;

import io.okkio.common.AbstractAuditingEntity;
import io.okkio.common.EntityStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CUSTOMER_SUPPORTS")
public class CustomerSupports extends AbstractAuditingEntity implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION" ,columnDefinition="TEXT")
    private String description;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private EntityStatus status;
}
