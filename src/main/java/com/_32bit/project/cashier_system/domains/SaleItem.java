package com._32bit.project.cashier_system.domains;

import jakarta.persistence.*;

@Entity
@Table(name = "sale_item")
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private Double quantity;

    private Double value;

    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale")
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer")
    private Offer offer;

    public SaleItem() {
        this.deleted = false;
    }

    public SaleItem(Double quantity, Sale sale, Product product, Offer offer) {
        this.quantity = quantity;
        this.deleted = false;
        this.sale = sale;
        this.product = product;
        this.offer = offer;
        this.value = this.quantity * this.product.getPrice();
    }

    public Long getId() {
        return id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    @Override
    public String toString() {
        return "SaleItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", value=" + value +
                ", deleted=" + deleted +
                ", sale=" + sale +
                ", product=" + product +
                ", offer=" + offer +
                '}';
    }
}