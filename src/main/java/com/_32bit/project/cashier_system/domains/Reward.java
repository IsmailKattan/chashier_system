package com._32bit.project.cashier_system.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reward")
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    @NotBlank
    @Size(max = 150)
    private String description;

    @Column(name = "discount_percentage")
    private Double discountPercentage;

    @Column(name = "offer_value")
    private Double offerValue;

    private Boolean deleted;

    @OneToMany(mappedBy = "reward",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Offer> offers = new ArrayList<>();

    public Reward() {
        this.deleted = false;
    }

    public Reward(String description, Double offerValue) {
        this.description = description;
        this.offerValue = offerValue;
        this.deleted = false;
    }

    public Reward(String description, Double discountPercentage, Double offerValue) {
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.offerValue = offerValue;
        this.deleted = false;
    }

    public Long getId() {
        return id;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Double getOfferValue() {
        return offerValue;
    }

    public void setOfferValue(Double offerValue) {
        this.offerValue = offerValue;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Reward{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", discountPercentage=" + discountPercentage +
                ", offerValue=" + offerValue +
                ", deleted=" + deleted +
                ", offers=" + offers +
                '}';
    }
}
