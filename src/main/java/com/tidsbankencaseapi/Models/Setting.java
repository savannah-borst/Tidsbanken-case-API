package com.tidsbankencaseapi.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int settingId;

    @NotBlank
    @Column(nullable = false)
    private int maxVacationDays;

    //Relation with Employee
    @OneToMany
    @JoinColumn(name = "setting_id")
    public List<Employee> employees;

    //-----GETTERS-----
    public int getSettingId() {
        return settingId;
    }

    public int getMaxVacationDays() {
        return maxVacationDays;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    //-----SETTERS-----
    public void setSettingId(int settingId) {
        this.settingId = settingId;
    }

    public void setMaxVacationDays(int maxVacationDays) {
        this.maxVacationDays = maxVacationDays;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
