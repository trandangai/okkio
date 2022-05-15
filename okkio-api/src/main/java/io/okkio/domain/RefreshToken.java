package io.okkio.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "REFRESH_TOKEN")
public class RefreshToken implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "EXPIRE_DATE")
    private Instant expiryDate;

    @Column(name = "USER_ID")
    private Long userId;
}
