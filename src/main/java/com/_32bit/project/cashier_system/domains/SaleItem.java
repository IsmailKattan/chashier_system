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

    private Double total;

    @Column(name = "discount_rate")
    private Double discountRate;

    @Column(name = "discounted_price")
    private Double discountedPrice;

    private Boolean deleted = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sale")
    private Sale sale;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product")
    private Product product;




}
