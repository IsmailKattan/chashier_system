package com._32bit.project.cashier_system.domains;

import com._32bit.project.cashier_system.domains.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
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

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private Double total;

    @Column(name = "amount_cash_paid")
    private Double amountCashPaid;

    @Column(name = "amount_card_paid")
    private Double amountCardPaid;

    @Column(name = "amount_paid")
    private Double amountPaid;

    @Column(name = "received_money")
    private Double receivedMoney;

    private Double change;

    private Boolean deleted = false;

    private Boolean isDiscounted = false;

    @Column(name = "discount_rate")
    private Double discountRate;

    @Column(name = "discounted_total")
    private Double discountedTotal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "soldBy")
    private TeamMember soldBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "session")
    private Session session;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SaleItem> saleItems = new ArrayList<>();

}
