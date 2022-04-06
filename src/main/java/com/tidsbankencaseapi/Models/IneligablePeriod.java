package com.tidsbankencaseapi.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class IneligablePeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int ineligablePeriodId;

    @NotBlank
    @Column(nullable = false)
    public Date periodStart;

    @NotBlank
    @Column(nullable = false)
    public Date periodEnd;
}
