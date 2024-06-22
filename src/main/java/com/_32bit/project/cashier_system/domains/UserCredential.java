package com._32bit.project.cashier_system.domains;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user_credential")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCredential implements UserDetails {

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

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    private Boolean deleted;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roleList =roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .toList();
        return roleList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
