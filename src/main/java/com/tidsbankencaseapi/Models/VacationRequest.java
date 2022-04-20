package com.tidsbankencaseapi.Models;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    private Integer requestId;

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
    private Date dateCreated = new Date();

    @Column
    private Date dateUpdated;

    @NotBlank
    @Column
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    //Relation with Owner
    @JsonGetter("requestOwner")
    public String get_requestOwner() {
        if (requestOwner != null) {
            return "/employee/" + requestOwner.getEmployeeId();
        } else {
            return null;
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_owner_id")
    private Employee requestOwner;

    //Relation with admin
    @JsonGetter("moderator")
    public String moderator() {
        if (moderator != null) {
            return  "/employee/" + moderator.getEmployeeId();
        } else {
            return null;
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id")
    private Employee moderator;

    //Relation with Comment
    @JsonGetter("comment")
    public List<String> get_comments() {
        if (comment != null) {
            return comment.stream()
                    .map(commentItem -> {
                        return  "/request/" + commentItem.getVacationRequest().getRequestId() + "/comment/" + commentItem.getCommentId();
                    }).collect(Collectors.toList());
        }
        return null;
    }

    @OneToMany(mappedBy = "vacationRequest", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<Comment> comment;

    //-----GETTERS-----
    public Integer getRequestId() {
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

    public Employee getRequestOwner() {
        return requestOwner;
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

    public void setRequestOwner(Employee owner) {
        this.requestOwner = owner;
    }

    public void setModerator(Employee moderator) {
        this.moderator = moderator;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }
}
