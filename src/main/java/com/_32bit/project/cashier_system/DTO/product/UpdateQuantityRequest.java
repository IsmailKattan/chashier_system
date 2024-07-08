package com._32bit.project.cashier_system.DTO.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateQuantityRequest {
    Long id;
    Double quantity;
}
