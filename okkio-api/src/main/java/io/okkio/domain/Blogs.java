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
@Table(name = "BLOGS")
public class Blogs extends AbstractAuditingEntity implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DETAILS")
    private String details;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "IMAGE_ID")
    private Long imageId;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private EntityStatus status;
}
