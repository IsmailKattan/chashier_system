package com._32bit.project.cashier_system.domains;

import com._32bit.project.cashier_system.couponType.CouponType;
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
    private Date startDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date",nullable = false)
    private Date endDate;
    @Column(nullable = false)
    private Double offerLimit;

    private boolean deleted;

    @OneToMany(mappedBy = "offer",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Sale> sales = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couponType")
    private CouponType couponType;


    public Offer() {
        this.deleted = false;
    }

    public Offer(String description, Date startDate, Date endDate, Double offerLimit, CouponType couponType) {
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.offerLimit = offerLimit;
        this.couponType = couponType;
        this.deleted = false;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getOfferLimit() {
        return offerLimit;
    }

    public void setOfferLimit(Double offerLimit) {
        this.offerLimit = offerLimit;
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
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", offerLimit=" + offerLimit +
                ", deleted=" + deleted +
                ", couponType=" + couponType +
                '}';
    }
}
