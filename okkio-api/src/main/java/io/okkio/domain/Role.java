package io.okkio.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ROLE")
public class Role implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "ROLE_NAME")
    private String roleName;

    @Column(name = "STATUS")
    private String status;

}
