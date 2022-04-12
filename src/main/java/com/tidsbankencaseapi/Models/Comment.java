package com.tidsbankencaseapi.Models;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "vacationrequest_comment",
            joinColumns = {@JoinColumn(name = "comment_id")},
            inverseJoinColumns = {@JoinColumn(name = "request_id")}
    )
    public VacationRequest vacationRequest;

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
