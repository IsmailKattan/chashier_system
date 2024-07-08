package com._32bit.project.cashier_system.domains;

import com._32bit.project.cashier_system.domains.enums.Category;
import com._32bit.project.cashier_system.domains.enums.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotBlank
    @Size(max = 60)
    private String name;

    @NotBlank
    @Size(max = 60)
    private String brand;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Double quantity;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false, name = "is_discounted")
    private Boolean isDiscounted;

    @Column(nullable = false, name = "discount_rate")
    private Double discountRate;

    @Column(nullable = false, name = "discounted_price")
    private Double discountedPrice;

    private Boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inserted_by")
    private TeamMember insertedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer")
    private Offer offer;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleItem> saleItems = new ArrayList<>();

}
