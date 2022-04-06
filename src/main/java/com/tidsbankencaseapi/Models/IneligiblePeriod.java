package com.tidsbankencaseapi.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class IneligiblePeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int ineligiblePeriodId;

    @NotBlank
    @Column(nullable = false)
    public Date periodStart;

    @NotBlank
    @Column(nullable = false)
    public Date periodEnd;
}
