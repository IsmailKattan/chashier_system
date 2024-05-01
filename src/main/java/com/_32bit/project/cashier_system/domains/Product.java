package com._32bit.project.cashier_system.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
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

    private Double quantity;
    @Enumerated(EnumType.STRING)
    private Unit unit;
    @Column(nullable = false)
    private Double price;
    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inserted_by")
    private TeamMember insertedBy;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SaleItem> saleItems = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "product__product_group",
        joinColumns = @JoinColumn(name = "Product_id"),
        inverseJoinColumns = @JoinColumn(name = "product_group_id"))
    private Set<ProductGroup> productGroup = new HashSet<>();

    public Product() {
        this.deleted = false;
    }

    public Product(String name, String brand, Unit unit, Double price, TeamMember insertedBy) {
        this.name = name;
        this.brand = brand;
        this.unit = unit;
        this.price = price;
        this.deleted = false;
        this.insertedBy = insertedBy;
    }

    public Long getId() {
        return id;
    }

    public List<SaleItem> getSaleItems() {
        return saleItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public TeamMember getInsertedBy() {
        return insertedBy;
    }

    public void setInsertedBy(TeamMember insertedBy) {
        this.insertedBy = insertedBy;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", quantity=" + quantity +
                ", unit=" + unit +
                ", price=" + price +
                ", deleted=" + deleted +
                ", insertedBy=" + insertedBy +
                '}';
    }
}
