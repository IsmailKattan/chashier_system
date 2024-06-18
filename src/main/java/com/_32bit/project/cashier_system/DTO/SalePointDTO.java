package com._32bit.project.cashier_system.DTO;

import lombok.*;

import java.sql.Time;
import java.util.Date;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class SalePointDTO {

    private final long id;
    @Setter
    private String name;
    private final Date creatingDate;
    private final Time creatingTime;
    @Setter
    private String Address;
    @Setter
    private boolean deleted;
    private final TeamMemberDTO createdBy;

}
