package com._32bit.project.cashier_system.domains.enums;

public enum ERole {

    ROLE_ADMIN,

    ROLE_MANAGER,

    ROLE_CASHIER;

    public static boolean contains(String s) {
        for (ERole role : values()) {
            if (role.name().equals(s)) {
                return true;
            }
        }
        return false;
    }
}
