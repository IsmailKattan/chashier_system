package com._32bit.project.cashier_system.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class RoleDTO {

    private final long id;
    @Setter
    private String name;
    @Setter
    private boolean deleted;

}
