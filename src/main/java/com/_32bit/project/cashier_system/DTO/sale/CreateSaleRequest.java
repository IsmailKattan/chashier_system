package com._32bit.project.cashier_system.DTO.sale;

import com._32bit.project.cashier_system.DTO.saleItem.ItemOfSaleDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSaleRequest {
    private Long salePointId;
    private List<ItemOfSaleDto> items;
}
