package com._32bit.project.cashier_system.DTO.sale;

import com._32bit.project.cashier_system.domains.Sale;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class SaleSpecification {
    public static Specification<Sale> hasDateBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, cb) -> {
            if (startDate == null && endDate == null) {
                return null;
            }
            if (startDate == null) {
                return cb.lessThanOrEqualTo(root.get("saleDate"), endDate);
            }
            if (endDate == null) {
                return cb.greaterThanOrEqualTo(root.get("saleDate"), startDate);
            }
            return cb.between(root.get("saleDate"), startDate, endDate);
        };
    }

    public static Specification<Sale> isPaid(Boolean isPaid) {
        return (root, query, cb) -> {
            if (isPaid == null) {
                return null;
            }
            return cb.equal(root.get("isPaid"), isPaid);
        };
    }

    public static Specification<Sale> isPosted(Boolean isPosted) {
        return (root, query, cb) -> {
            if (isPosted == null) {
                return null;
            }
            return cb.equal(root.get("isPosted"), isPosted);
        };
    }

    public static Specification<Sale> isInvoiced(Boolean isInvoiced) {
        return (root, query, cb) -> {
            if (isInvoiced == null) {
                return null;
            }
            return cb.equal(root.get("isInvoiced"), isInvoiced);
        };
    }
}
