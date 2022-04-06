package com.tidsbankencaseapi.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int employeeId;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    public String name;

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
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    public List<VacationRequest> vacationRequests;// = this.getVacationRequests();


    //Relation with Comment
    @OneToMany
    @JoinColumn(name = "employee_id")
    List<Comment> comments;

}
