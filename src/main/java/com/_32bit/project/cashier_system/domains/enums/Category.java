package com._32bit.project.cashier_system.domains.enums;

public enum Category {

    FOOD,

    DRINK,

    ELECTRONIC,

    CLOTHING,

    BOOK,

    STATIONERY,

    COSMETIC,

    TOILETRY,

    OTHER;


    public static boolean contains(String value) {
        for (Category category : Category.values()) {
            if (category.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
