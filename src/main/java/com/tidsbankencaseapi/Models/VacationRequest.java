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
    private int requestId;

    @NotBlank
    @Size(max = 100)
    @Column(length = 100, nullable = false)
    private String title;

    @NotBlank
    @Column(nullable = false)
    private Date periodStart;

    @NotBlank
    @Column(nullable = false)
    private Date periodEnd;

    @NotBlank
    @Column(nullable = false)
    private Date dateCreated;

    @NotBlank
    @Column(nullable = false)
    private Date dateUpdated;

    @NotBlank
    @Column
    @Enumerated(EnumType.ORDINAL)
    private Status status;


    //Relation with Owner
    @JsonGetter("owner")
    public String get_owner() {
        if (owner != null) {
            return "Owner: " + owner.getEmployeeId() + " " + owner.getFirst_name() + " " + owner.getLast_name();
        } else {
            return null;
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employee_vacationrequest",
            joinColumns = {@JoinColumn(name = "request_id")},
            inverseJoinColumns = {@JoinColumn(name = "owner_id")}
    )
    @JoinColumn(name = "owner_id")
    public Employee owner;


    //Relation with admin
    @JsonGetter("moderator")
    public String moderator() {
        if (moderator != null) {
            return "Moderator: " + moderator.getEmployeeId() + " " + moderator.getFirst_name() + " " + moderator.getLast_name();
        } else {
            return null;
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id")
    public Employee moderator;

    //Relation with Comment
    @JsonGetter("comment")
    public List<String> get_comments() {
        if (comment != null) {
            return comment.stream()
                    .map(commentItem -> {
                        return commentItem.getCommentId() + " " + commentItem.getMessage();
                    }).collect(Collectors.toList());
        }
        return null;
    }

    @OneToMany(mappedBy = "vacationRequest", fetch = FetchType.LAZY)
    public List<Comment> comment = this.getComment();

    //-----GETTERS-----
    public int getRequestId() {
        return requestId;
    }

    public String getTitle() {
        return title;
    }

    public Date getPeriodStart() {
        return periodStart;
    }

    public Date getPeriodEnd() {
        return periodEnd;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public Status getStatus() {
        return status;
    }

    public Employee getOwner() {
        return owner;
    }

    public Employee getModerator() {
        return moderator;
    }

    public List<Comment> getComment() {
        return comment;
    }

    //-----SETTERS-----
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPeriodStart(Date periodStart) {
        this.periodStart = periodStart;
    }

    public void setPeriodEnd(Date periodEnd) {
        this.periodEnd = periodEnd;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setOwner(Employee owner) {
        this.owner = owner;
    }

    public void setModerator(Employee moderator) {
        this.moderator = moderator;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }
}
