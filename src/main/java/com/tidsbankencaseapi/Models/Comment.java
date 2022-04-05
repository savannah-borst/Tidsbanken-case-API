package com.tidsbankencaseapi.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

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

    //FK request_id

    //FK user_id
}
