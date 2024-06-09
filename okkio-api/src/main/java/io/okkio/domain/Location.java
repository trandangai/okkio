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
@Table(name = "LOCATION")
public class Location extends AbstractAuditingEntity implements Serializable  {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "OPEN_TIME")
    private String openTime;

    @Column(name = "OPEN_DAY")
    private String openDay;

    @Column(name = "CONCEPT_STORE")
    private String conceptStore;

    @Column(name = "IMAGES")
    private String images;

    @Column(name = "PARKING_LOT")
    private String parkingLot;

    @Column(name = "STORE")
    private String store;
}
