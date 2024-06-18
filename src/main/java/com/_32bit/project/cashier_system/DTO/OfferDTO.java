package com._32bit.project.cashier_system.DTO;


import lombok.*;

import java.util.Date;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class OfferDTO {
    private final long id;
    @Setter
    private String name;
    @Setter
    private String description;
    private final Date insertionDate;
    @Setter
    private Date startDate;
    @Setter
    private Date endDate;
    @Setter
    private int getCount;
    @Setter
    private int payFor;
    @Setter
    private boolean deleted;

}
