package com._32bit.project.cashier_system.domains;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "invoice")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invoice {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false)
    @Id
    private Long id;

    @Column(name = "extraction_date")
    @Temporal(TemporalType.DATE)
    private LocalDate extractionDate;

    @Column(name = "extraction_time")
    @Temporal(TemporalType.TIME)
    private LocalTime extractionTime;

    @OneToOne(fetch = FetchType.EAGER)
    private Sale sale;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "soldBy")
    private TeamMember soldBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sale_point")
    private SalePoint salePoint;
}
