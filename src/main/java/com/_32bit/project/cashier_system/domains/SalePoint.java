package com._32bit.project.cashier_system.domains;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sale_point")
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SalePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    private String name;

    @Column(name = "creating_date")
    @Temporal(TemporalType.DATE)
    private LocalDate creatingDate;

    @Column(name = "creating_time")
    @Temporal(TemporalType.TIME)
    private LocalTime creatingTime;

    private String address;

    private Boolean deleted = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "createdBy")
    private TeamMember createdBy;

    @OneToMany(mappedBy = "salePoint", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Session> sessions;

    @OneToMany(mappedBy = "salePoint", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<TeamMember> teamMembers;

    @OneToMany(mappedBy = "salePoint", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Invoice> invoices;
}
