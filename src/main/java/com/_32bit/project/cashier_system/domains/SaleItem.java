package com._32bit.project.cashier_system.domains;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sale_item")
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private Double quantity;

    @Column(name = "paid_for")
    private Double paidFor;

    private Double total;

    @Column(nullable = false,name = "is_offer_applied")
    private Boolean isOfferApplied = false;

    @Column(nullable = false,name = "is_discounted")
    private Boolean isDiscounted = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "offer")
    private Offer offer;

    private Boolean deleted = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sale")
    private Sale sale;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product")
    private Product product;




}
