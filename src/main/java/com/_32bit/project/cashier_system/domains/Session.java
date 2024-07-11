package com._32bit.project.cashier_system.domains;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" ,updatable = false)
    private Long id;

    @Column(name = "opening_date")
    @Temporal(TemporalType.DATE)
    private LocalDate openingDate;

    @Column(name = "opening_time")
    @Temporal(TemporalType.TIME)
    private LocalTime openingTime;

    @Column(name = "closing_date")
    @Temporal(TemporalType.DATE)
    private LocalDate closingDate;

    @Column(name = "closing_time")
    @Temporal(TemporalType.TIME)
    private LocalTime closingTime;

    private Boolean deleted;

    private Boolean closed = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "openedBy")
    private TeamMember openedBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "closedBy")
    private TeamMember closedBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "salePoint")
    private SalePoint salePoint;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Sale> sales;

}
