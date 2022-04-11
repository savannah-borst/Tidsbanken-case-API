package com.tidsbankencaseapi.Models;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
public class Employee {

    @Id
    public String employeeId;

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
    @JsonGetter("vacationRequests")
    public List<String> get_vacation_request() {
        if (vacationRequests != null) {
            return vacationRequests.stream()
                    .map(requestItem -> {
                        return requestItem.requestId + " " + requestItem.title;
                    }).collect(Collectors.toList());
        }
        return null;
    }

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<VacationRequest> vacationRequests = this.getVacationRequests();


    //Relation with Comment
    @OneToMany
    @JoinColumn(name = "employee_id")
    List<Comment> comments;


    //GETTERS
    public List<VacationRequest> getVacationRequests() {
        return vacationRequests;
    }
}
