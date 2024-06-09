package com._32bit.project.cashier_system.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "offer")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotBlank
    @Size(max = 80)
    private String name;

    @NotBlank
    @Size(max = 150)
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "insertion_date", nullable = false)
    private Date insertionsDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date",nullable = false)
    private Date starDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date",nullable = false)
    private Date endDate;

    @Column(name = "get_count")
    private Integer getCount;

    @Column(name = "pay_for")
    private Integer payFor;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    private Boolean deleted = false;


}
