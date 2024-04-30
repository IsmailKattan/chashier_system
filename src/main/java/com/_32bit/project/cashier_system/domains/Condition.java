package com._32bit.project.cashier_system.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "condition")
public class Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotBlank
    @Size(max = 150)
    private String description;

    @Column(name = "sold_count")
    private Double soldCount;

    private Boolean deleted;

    @OneToMany(mappedBy = "condition",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Offer> offers = new ArrayList<>();

    public Condition() {
        this.deleted = false;
    }

    public Condition(String description, Double soldCount) {
        this.description = description;
        this.soldCount = soldCount;
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

    public Double getSoldCount() {
        return soldCount;
    }

    public void setSoldCount(Double soldCount) {
        this.soldCount = soldCount;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", soldCount=" + soldCount +
                ", deleted=" + deleted +
                ", offers=" + offers +
                '}';
    }
}
