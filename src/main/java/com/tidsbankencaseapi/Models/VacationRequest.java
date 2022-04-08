package com.tidsbankencaseapi.Models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    public Status status;


    //Relation with Employee
    @JsonGetter("employee")
    public String employee() {
        if (employee != null) {
            return employee.employeeId + " " + employee.name;
        } else {
            return null;
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employee_vacationrequest",
            joinColumns = {@JoinColumn(name = "request_id")},
            inverseJoinColumns = {@JoinColumn(name = "employee_id")}
    )
    public Employee employee;


    //Relation with Comment
    @JsonGetter("comment")
    public List<String> get_comments() {
        if (comment != null) {
            return comment.stream()
                    .map(commentItem -> {
                        return "Comment: " + commentItem.commentId;
                    }).collect(Collectors.toList());
        }
        return null;
    }

    @OneToMany(mappedBy = "vacationRequest", fetch = FetchType.LAZY)
    public List<Comment> comment; //this.getComments();
}
