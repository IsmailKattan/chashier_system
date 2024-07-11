package com._32bit.project.cashier_system.domains.enums;

public enum PaymentMethod {
    kredi_karti,
    nakit,
    havale,
    mailorder,
    diger;

    public static Boolean contains(String paymentType) {
        for (PaymentMethod type : PaymentMethod.values()) {
            if (type.name().equalsIgnoreCase(paymentType)) {
                return true;
            }
        }
        return false;
    }
}
