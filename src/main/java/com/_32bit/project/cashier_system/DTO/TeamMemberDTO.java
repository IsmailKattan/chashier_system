package com._32bit.project.cashier_system.DTO;

import com._32bit.project.cashier_system.domains.Role;
import com._32bit.project.cashier_system.domains.SalePoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.Set;


@Data
public class TeamMemberDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private List<String> roles;
    private String phoneNumber;
    private String email;
    private String salePoint;
    private String password;
    private boolean deleted;

    public TeamMemberDTO(String firstname, String lastname, String username, List<String> roles, String phoneNumber, String email, String salePoint, boolean deleted) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.roles = roles;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.salePoint = salePoint;
        this.deleted = deleted;
    }
}
