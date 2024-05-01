package com._32bit.project.cashier_system.discount;

import com._32bit.project.cashier_system.productGroup.ProductGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotBlank
    @Size(max = 150)
    private String name;

    private Double discount;

    @Column(name = "discount_percentage")
    private Double discountPercentage;

    @Column(name = "buy_num")
    private Integer buyNum;

    @Column(name = "get_num")
    private Integer getNum;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;

    private Boolean deleted;

    @OneToMany(mappedBy = "discount",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<ProductGroup> productGroups;

    public Discount() {
        this.deleted = false;
    }

    public Discount(String name, Double discount, Double discountPercentage, Integer buyNum, Integer getNum, Date startDate, Date endDate) {
        this.name = name;
        this.discount = discount;
        this.discountPercentage = discountPercentage;
        this.buyNum = buyNum;
        this.getNum = getNum;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deleted = false;
    }

    public Discount(String name, Double discount, Double discountPercentage, Date startDate, Date endDate) {
        this.name = name;
        this.discount = discount;
        this.discountPercentage = discountPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deleted = false;
    }

    public Discount(String name, Integer buyNum, Integer getNum, Date startDate, Date endDate) {
        this.name = name;
        this.buyNum = buyNum;
        this.getNum = getNum;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deleted = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public Integer getGetNum() {
        return getNum;
    }

    public void setGetNum(Integer getNum) {
        this.getNum = getNum;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public List<ProductGroup> getProductGroups() {
        return productGroups;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", discount=" + discount +
                ", discountPercentage=" + discountPercentage +
                ", buyNum=" + buyNum +
                ", getNum=" + getNum +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", deleted=" + deleted +
                '}';
    }
}
