package com._32bit.project.cashier_system.couponType;

import com._32bit.project.cashier_system.coupon.Coupon;
import com._32bit.project.cashier_system.offer.Offer;
import com._32bit.project.cashier_system.enums.DiscountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "coupon_type")
public class CouponType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotBlank
    @Size(max = 60)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type")
    private DiscountType discountType;

    @Column(name = "discount_value")
    private Double discountValue;

    private Boolean deleted;

    @OneToMany(mappedBy = "couponType",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Offer> offers = new ArrayList<>();

    @OneToMany(mappedBy = "couponType" ,fetch =  FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Coupon> coupons = new ArrayList<>();

    public CouponType() {
        this.deleted = false;
    }

    public CouponType(String name, DiscountType discountType, Double discountValue) {
        this.name = name;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.deleted = false;
    }

    public Long getId() {
        return id;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "CouponType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", discountType=" + discountType +
                ", discountValue=" + discountValue +
                ", deleted=" + deleted +
                '}';
    }
}
