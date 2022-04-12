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
    @Column
    @Enumerated(EnumType.ORDINAL)
    public Status status;


    //Relation with Owner
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employee_vacationrequest",
            joinColumns = {@JoinColumn(name = "request_id")},
            inverseJoinColumns = {@JoinColumn(name = "owner_id")}
    )
    @JoinColumn(name = "owner_id")
    public Employee owner = this.getOwner();

    @JsonGetter("owner")
    public String get_owner() {
        if (owner != null) {
            return "Owner: " + owner.employeeId + " " + owner.first_name + " " + owner.last_name;
        } else {
            return null;
        }
    }

    //Relation with admin
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id")
    public Employee moderator;

    @JsonGetter("moderator")
    public String moderator() {
        if (moderator != null) {
            return "Moderator: " + moderator.employeeId + " " + moderator.first_name + " " + moderator.last_name;
        } else {
            return null;
        }
    }

    //Relation with Comment
    @JsonGetter("comment")
    public List<String> get_comments() {
        if (comment != null) {
            return comment.stream()
                    .map(commentItem -> {
                        return commentItem.commentId + " " + commentItem.message;
                    }).collect(Collectors.toList());
        }
        return null;
    }

    @OneToMany(mappedBy = "vacationRequest", fetch = FetchType.LAZY)
    public List<Comment> comment = this.getComment();

    //GETTERS
    public List<Comment> getComment() {
        return comment;
    }

    public Employee getOwner() {
        return owner;
    }
}
