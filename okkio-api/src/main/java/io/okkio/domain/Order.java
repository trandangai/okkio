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
@Table(name = "`ORDER`")
public class Order extends AbstractAuditingEntity implements Serializable  {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ORDER_CODE")
    private String orderCode;

    // DRAFT , WAITING, APPROVED, CANCELLED, CANCELLED
    @Column(name = "OKKIO_STATUS_ID")
    private Long okkioStatusId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "USER_ID")
    private Long userId;
}
