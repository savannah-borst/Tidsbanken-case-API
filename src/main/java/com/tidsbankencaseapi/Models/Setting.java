package com.tidsbankencaseapi.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int settingId;

    @NotBlank
    @Column(nullable = false)
    public int maxVacationDays;


    //Relation with Employee
    @OneToMany
    @JoinColumn(name = "setting_id")
    List<Employee> employees;
}
