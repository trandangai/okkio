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
@Table(name = "CAREERS")
public class Careers extends AbstractAuditingEntity implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PART_TIME")
    private Boolean isPartTime;

    @Column(name = "FULL_TIME")
    private Boolean isFullTime;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private EntityStatus status;
}
