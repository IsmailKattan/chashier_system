package com._32bit.project.cashier_system.domains;

import com._32bit.project.cashier_system.domains.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "sale")
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Temporal(TemporalType.DATE)
    private LocalDate saleDate;

    @Temporal(TemporalType.TIME)
    private LocalTime saleTime;

    private Double total;

    private Boolean deleted = false;

    @Column(name = "is_paid")
    private Boolean isPaid = false;

    @Column(name = "is_posted")
    private Boolean isPosted = false;

    @Column(name = "is_invoiced")
    private Boolean isInvoiced = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "soldBy")
    private TeamMember soldBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "session")
    private Session session;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SaleItem> saleItems = new ArrayList<>();

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    @OneToOne(mappedBy = "sale", cascade = CascadeType.ALL,orphanRemoval = true)
    private Invoice invoice;

}
