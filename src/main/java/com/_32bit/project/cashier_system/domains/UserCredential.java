package com._32bit.project.cashier_system.domains;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "user_credential")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCredential implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;

    @Column(unique = true,name = "phone_number")
    private String phoneNumber;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="user_credential_roles",
            joinColumns = @JoinColumn(name = "user_credential_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private List<Role> roles = new ArrayList<>();

    @OneToOne(mappedBy = "userCredential", cascade = CascadeType.ALL)
    private TeamMember teamMember;

    private Boolean deleted;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
