package com._32bit.project.cashier_system.domains.enums;

public enum Unit {
    kg,
    g,
    adet,
    koli,
    paket,
    deste,
    duzine,
    other;

    public static boolean contains(String value) {
        for (Unit unit : Unit.values()) {
            if (unit.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
