package com.tidsbankencaseapi.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
public class VacationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int requestId;

    @NotBlank
    @Size(max = 100)
    @Column(length = 100, nullable = false)
    public String title;

    @NotBlank
    @Column(nullable = false)
    public Date periodStart;

    @NotBlank
    @Column(nullable = false)
    public Date periodEnd;

    @NotBlank
    @Column(nullable = false)
    public Date dateCreated;

    @NotBlank
    @Column(nullable = false)
    public Date dateUpdated;

    @NotBlank
    @Column
    @Enumerated(EnumType.ORDINAL)
    public Status status;


    //Relation with Employee
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employee_vacationrequest",
            joinColumns = {@JoinColumn(name = "request_id")},
            inverseJoinColumns = {@JoinColumn(name = "employee_id")}
    )
    public Employee employee;


    //Relation with Comment
    @OneToMany(mappedBy = "vacationRequest", fetch = FetchType.LAZY)
    public List<Comment> comment; //this.getComments();
}
