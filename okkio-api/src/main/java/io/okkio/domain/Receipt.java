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
@Table(name = "RECEIPT")
public class Receipt extends AbstractAuditingEntity implements Serializable  {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DETAIL")
    private String detail;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "OKKIO_STATUS_ID")
    private Long okkioStatusId;

    @Column(name = "ORDER_ID")
    private Long orderId;
}
