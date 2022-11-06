package io.okkio.domain;

import io.okkio.common.AbstractAuditingEntity;
import io.okkio.common.EntityStatus;
import io.okkio.common.ImageType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "IMAGE")
public class Image extends AbstractAuditingEntity implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "IMAGE_CONTENT", columnDefinition = "LONGTEXT")
    private String imageContent;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private ImageType type;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private EntityStatus status;
}
