package com.tidsbankencaseapi.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int settingId;

    @NotBlank
    @Column(nullable = false)
    public int maxVacationDays;
}
