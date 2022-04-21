package com.tidsbankencaseapi.Models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class IneligiblePeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ineligiblePeriodId;

    @NotBlank
    @Column(nullable = false)
    private Date periodStart;

    @NotBlank
    @Column(nullable = false)
    private Date periodEnd;

    //-----GETTERS-----
    public Integer getIneligiblePeriodId() {
        return ineligiblePeriodId;
    }

    public Date getPeriodStart() {
        return periodStart;
    }

    public Date getPeriodEnd() {
        return periodEnd;
    }

    //-----SETTERS-----
    public void setIneligiblePeriodId(int ineligiblePeriodId) {
        this.ineligiblePeriodId = ineligiblePeriodId;
    }

    public void setPeriodStart(Date periodStart) {
        this.periodStart = periodStart;
    }

    public void setPeriodEnd(Date periodEnd) {
        this.periodEnd = periodEnd;
    }
}
