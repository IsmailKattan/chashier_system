package com._32bit.project.cashier_system.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ObjectWithMessageResponse {

    private MessageResponse messageResponse;
    private Object object;

}
