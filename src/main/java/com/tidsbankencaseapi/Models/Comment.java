package com.tidsbankencaseapi.Models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    @NotBlank
    @Size(max = 255)
    @Column
    private String message;

    @NotBlank
    @Column(nullable = false)
    private Date dateCreated;

    @NotBlank
    @Column(nullable = false)
    private Date dateUpdated;


    //relation with VacationRequest
    @JsonGetter("vacationRequest")
    public String get_request() {
        if (vacationRequest != null) {
            return "Request: " + vacationRequest.getRequestId() + " " + vacationRequest.getTitle();
        }
        return null;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    public VacationRequest vacationRequest;

    //Relation Employee
    @JsonGetter("commentOwner")
    public String get_owner() {
        if (commentOwner != null) {
            return "Owner: " + commentOwner.getEmployeeId() + " " + commentOwner.getFirst_name() + " " + commentOwner.getLast_name();
        }
        return null;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentOwner_id")
    public Employee commentOwner;

    //-----GETTERS-----
    public int getCommentId() {
        return commentId;
    }

    public String getMessage() {
        return message;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public VacationRequest getVacationRequest() {
        return vacationRequest;
    }

    //-----SETTERS-----
    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public void setVacationRequest(VacationRequest vacationRequest) {
        this.vacationRequest = vacationRequest;
    }
}
