package com._32bit.project.cashier_system.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "offer")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotBlank
    @Size(max = 150)
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date",nullable = false)
    private Date starDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date",nullable = false)
    private Date endDate;
    @Column(nullable = false)
    private Double limit;

    private boolean deleted;

    @OneToMany(mappedBy = "offer",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Sale> sales = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_type")
    private CouponType couponType;


    public Offer() {
        this.deleted = false;
    }

    public Offer(String description, Date starDate, Date endDate, Double limit, CouponType couponType) {
        this.description = description;
        this.starDate = starDate;
        this.endDate = endDate;
        this.limit = limit;
        this.couponType = couponType;
        this.deleted = false;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStarDate() {
        return starDate;
    }

    public void setStarDate(Date starDate) {
        this.starDate = starDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public CouponType getCouponType() {
        return couponType;
    }

    public void setCouponType(CouponType couponType) {
        this.couponType = couponType;
    }

    public Long getId() {
        return id;
    }

    public List<Sale> getSales() {
        return sales;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", starDate=" + starDate +
                ", endDate=" + endDate +
                ", limit=" + limit +
                ", deleted=" + deleted +
                ", couponType=" + couponType +
                '}';
    }
}
