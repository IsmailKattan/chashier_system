package com._32bit.project.cashier_system.domains;

import com._32bit.project.cashier_system.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
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
    private Long Id;

    @Temporal(TemporalType.DATE)
    private Date saleDate;

    @Temporal(TemporalType.TIME)
    private Time saleTime;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private Double total;

    @Column(name = "received_money")
    private Double receivedMoney;

    private Double change;

    private Boolean deleted = false;

    @Column(name = "discount_rate")
    private Double discountRate;

    @Column(name = "discounted_total")
    private Double discountedTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "soldBy")
    private TeamMember soldBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session")
    private Session session;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SaleItem> saleItems = new ArrayList<>();

}
