package com._32bit.project.cashier_system.domains;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "session")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id" ,updatable = false)
    private Long id;

    @Column(name = "opening_date")
    @Temporal(TemporalType.DATE)
    private Date openingDate;

    @Column(name = "opening_time")
    @Temporal(TemporalType.TIME)
    private Time openingTime;

    @Column(name = "closing_date")
    @Temporal(TemporalType.DATE)
    private Date closingDate;

    @Column(name = "closing_time")
    @Temporal(TemporalType.TIME)
    private Time closingTime;

    @Column(name = "opening_cash")
    private Double openingCash;

    @Column(name = "closing_cash")
    private Double closingCash;

    private Double balance;

    private Boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "openedBy")
    private TeamMember openedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salePoint")
    private SalePoint salePoint;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Sale> sales;

}
