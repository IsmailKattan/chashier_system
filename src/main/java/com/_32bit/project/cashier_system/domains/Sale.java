package com._32bit.project.cashier_system.domains;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "sale")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long Id;
    @Temporal(TemporalType.DATE)
    private Date saleDate;
    @Temporal(TemporalType.TIME)
    private Time saleTime;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @Column(name = "sales_value")
    private Double salesValue;
    @Column(name = "received_money")
    private Double receivedMoney;
    private Double change;
    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "soldBy")
    private TeamMember soldBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer")
    private Offer offer;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SaleItem> saleItems = new ArrayList<>();

    public Sale() {
        this.deleted = false;
    }

    public Sale(Date saleDate, Time saleTime, PaymentType paymentType, Double receivedMoney, TeamMember soldBy, List<SaleItem> saleItems) {
        this.saleDate = saleDate;
        this.saleTime = saleTime;
        this.paymentType = paymentType;
        this.receivedMoney = receivedMoney;
        this.soldBy = soldBy;
        this.saleItems = saleItems;
        this.deleted = false;
        this.salesValue = 0.0;
        for (SaleItem saleItem : saleItems) {
            this.salesValue = +saleItem.getValue();
        }
        this.change = this.receivedMoney - this.salesValue;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return Id;
    }

    public List<SaleItem> getSaleItems() {
        return saleItems;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public Time getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(Time saleTime) {
        this.saleTime = saleTime;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Double getSalesValue() {
        return salesValue;
    }

    public void setSalesValue(Double salesValue) {
        this.salesValue = salesValue;
    }

    public Double getReceivedMoney() {
        return receivedMoney;
    }

    public void setReceivedMoney(Double receivedMoney) {
        this.receivedMoney = receivedMoney;
    }

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }

    public TeamMember getSoldBy() {
        return soldBy;
    }

    public void setSoldBy(TeamMember soldBy) {
        this.soldBy = soldBy;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "Id=" + Id +
                ", saleDate=" + saleDate +
                ", saleTime=" + saleTime +
                ", paymentType=" + paymentType +
                ", salesValue=" + salesValue +
                ", receivedMoney=" + receivedMoney +
                ", change=" + change +
                ", deleted=" + deleted +
                ", soldBy=" + soldBy +
                ", saleItems=" + saleItems +
                '}';
    }
}
