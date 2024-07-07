package com._32bit.project.cashier_system.domains;

import com._32bit.project.cashier_system.domains.enums.ERole;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "role")
@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ERole name;

    private Boolean deleted = false;

}
