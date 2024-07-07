package com._32bit.project.cashier_system.DTO.session;


import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateSessionRequest {

    private Long salePointId;


    public CreateSessionRequest(Long salePointId) {
        this.salePointId = salePointId;
    }
}
