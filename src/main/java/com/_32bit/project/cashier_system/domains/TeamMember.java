package com._32bit.project.cashier_system.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "team_member",uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username",
                "phone_number",
                "email"
        })
})
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" ,updatable = false)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Column(unique = true,name = "phone_number")
    private String phoneNumber;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="team_member_roles",
            joinColumns = @JoinColumn(name = "team_member_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @NotBlank
    @Size(max = 40)
    private String firstname;

    @NotBlank
    @Size(max = 40)
    private String lastname;


    @Column(name = "insertion_date")
    @Temporal(TemporalType.DATE)
    private LocalDate insertionDate;

    @Column(name = "insertion_Time")
    @Temporal(TemporalType.TIME)
    private LocalTime insertionTime;

    private Boolean deleted = false;

    @OneToMany(mappedBy = "insertedBy",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "soldBy",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sale> sales = new ArrayList<>();

    @OneToMany(mappedBy = "openedBy", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Session> sessions = new ArrayList<>();

    @OneToMany(mappedBy = "closedBy", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Session> closedSessions = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SalePoint> salePoints = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "sale_point_id", nullable = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private SalePoint salePoint;

    @OneToMany(mappedBy = "soldBy", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Invoice> invoices = new ArrayList<>();

}

