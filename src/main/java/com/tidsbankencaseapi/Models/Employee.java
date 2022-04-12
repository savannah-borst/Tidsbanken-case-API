package com.tidsbankencaseapi.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name="employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer employeeId;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    public String first_name;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    public String last_name;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    public String emailAddress;

    @Size(max = 255)
    @Column
    public String profilePic;

    @Column
    public Boolean isAdmin;

    //Relation with Vacation Request
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    public List<VacationRequest> vacationRequests;// = this.getVacationRequests();

    //Relation with Vacation Request
    @OneToMany(mappedBy = "moderator", fetch = FetchType.LAZY)
    public List<VacationRequest> moderatedVacationRequests;

    //Relation with Comment
    @OneToMany
    @JoinColumn(name = "employee_id")
    List<Comment> comments;

}
