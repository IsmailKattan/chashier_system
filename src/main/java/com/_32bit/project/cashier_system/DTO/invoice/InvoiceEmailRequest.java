package com._32bit.project.cashier_system.DTO.invoice;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceEmailRequest {
    private Long invoiceId;
    private List<String> emailAddresses;

}
