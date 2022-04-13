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
    private int ineligiblePeriodId;

    @NotBlank
    @Column(nullable = false)
    private Date periodStart;

    @NotBlank
    @Column(nullable = false)
    private Date periodEnd;

    //Relation with Employee
    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Employee creator;

    @JsonGetter("creator")
    public String get_creator() {
        if (creator != null) {
            return "Ineligible period created by: " + creator.getFirst_name();
        }
        return null;
    }

    //-----GETTERS-----
    public int getIneligiblePeriodId() {
        return ineligiblePeriodId;
    }

    public Date getPeriodStart() {
        return periodStart;
    }

    public Date getPeriodEnd() {
        return periodEnd;
    }

    public Employee getCreator() {return creator;}

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

    public void setCreator(Employee creator) {this.creator = creator;}
}
