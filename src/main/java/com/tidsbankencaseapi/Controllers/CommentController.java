package com.tidsbankencaseapi.Controllers;

import com.tidsbankencaseapi.Repositories.CommentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@SecurityRequirement(name = "keycloak_implicit")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    //GET /request/:request_id/comment
    @Operation(summary = "Get all comments from a request")
    @GetMapping("/request/{requestId}")
    @PreAuthorize()
    public ResponseEntity<> getAllComments() {

    }

    //POST /request/:request_id/comment
    @Operation(summary = "Add comment")
    @PostMapping("/request/{requestId}/add")
    @PreAuthorize()
    public ResponseEntity<> addComment() {

    }

    //GET /request/:request_id/comment/:comment_id
    @Operation(summary = "Get specific comment from a request")
    @GetMapping("/request/{requestId}/{commentId]")
    @PreAuthorize()
    public ResponseEntity<> getSpecificComment() {

    }

    //PATCH /request/:request_id/comment/:comment_id
    @Operation(summary = "update specific comment from a request")
    @PatchMapping("/request/{requestId}/update/{commentId}")
    @PreAuthorize()
    public ResponseEntity<> updateComment() {

    }

    //DELETE /request/:request_id/comment/:comment_id
    @Operation(summary = "Delete specific comment from a request")
    @DeleteMapping("/request/{requestId}/delete/{commentId}")
    @PreAuthorize()
    public ResponseEntity<> deleteComment() {

    }
}
