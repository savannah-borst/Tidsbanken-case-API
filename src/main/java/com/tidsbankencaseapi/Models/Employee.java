package com.tidsbankencaseapi.Models;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Employee {

    @Id
    @NotBlank
    private String employeeId;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String first_name;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String last_name;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String emailAddress;

    @Size(max = 255)
    @Column
    private String profilePic;

    @Column
    private Boolean isAdmin = false;

    //Relation with Vacation Request
    @JsonGetter("vacationRequests")
    public List<String> get_vacation_request() {
        if (vacationRequests != null) {
            return vacationRequests.stream()
                    .map(requestItem -> {
                              return requestItem.getRequestId() + " " + requestItem.getTitle();
                            }).collect(Collectors.toList());
        }
        return null;
    }

    @OneToMany(mappedBy = "requestOwner", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<VacationRequest> vacationRequests = this.getVacationRequests();


    //Relation with Comment
    @JsonGetter("comments")
    public List<String> get_comments() {
        if (comments != null) {
            return comments.stream()
                    .map(commentItem -> {
                        return commentItem.getCommentId() + " " + commentItem.getMessage();
                    }).collect(Collectors.toList());
        }
        return null;
    }

    @OneToMany(mappedBy = "commentOwner", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<Comment> comments = this.getComments();


    //-----GETTERS-----
    public String getEmployeeId() {
        return employeeId;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public List<VacationRequest> getVacationRequests() {
        return vacationRequests;
    }

    public List<Comment> getComments() {
        return comments;
    }


    //-----SETTERS-----
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public void setVacationRequests(List<VacationRequest> vacationRequests) {
        this.vacationRequests = vacationRequests;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
