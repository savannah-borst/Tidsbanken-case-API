package com.tidsbankencaseapi.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int commentId;

    @NotBlank
    @Size(max = 255)
    @Column
    public String message;

    @NotBlank
    @Column(nullable = false)
    public Date dateCreated;

    @NotBlank
    @Column(nullable = false)
    public Date dateUpdated;


    //relation with VacationRequest
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "vacationrequest_comment",
            joinColumns = {@JoinColumn(name = "comment_id")},
            inverseJoinColumns = {@JoinColumn(name = "request_id")}
    )
    public VacationRequest vacationRequest;
}
