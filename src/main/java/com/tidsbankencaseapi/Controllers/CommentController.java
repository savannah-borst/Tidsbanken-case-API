package com.tidsbankencaseapi.Controllers;

import com.tidsbankencaseapi.Models.Comment;
import com.tidsbankencaseapi.Models.Employee;
import com.tidsbankencaseapi.Models.VacationRequest;
import com.tidsbankencaseapi.Repositories.CommentRepository;
import com.tidsbankencaseapi.Repositories.EmployeeRepository;
import com.tidsbankencaseapi.Repositories.VacationRequestRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/comment")
@SecurityRequirement(name = "keycloak_implicit")
public class CommentController {

    private final CommentRepository commentRepository;
    private final VacationRequestRepository requestRepository;
    private final EmployeeRepository employeeRepository;

    public CommentController (CommentRepository commentRepository, VacationRequestRepository requestRepository, EmployeeRepository employeeRepository) {
        this.requestRepository = requestRepository;
        this.employeeRepository = employeeRepository;
        this.commentRepository = commentRepository;
    }


    //GET /request/:request_id/comment
    @Operation(summary = "Get all comments from a request")
    @GetMapping("/request/{requestId}")
    @PreAuthorize("hasAnyRole('user', 'administrator')")//ADMIN / USER protected
    public ResponseEntity<List<Comment>> getAllComments(@PathVariable Integer requestId, @AuthenticationPrincipal Jwt principal) {
        //most recent first
        //Optionally accepts appropriate query parameters.

        HttpStatus status;
        List<String> signedInRole = principal.getClaimAsStringList("roles"); //get roles from logged in employee
        String currentEmployeeId = principal.getClaimAsString("sub"); //Logged in employee ID
        List<Comment> comments = new ArrayList<>();
        VacationRequest testRequest = requestRepository.findById(requestId).get();
        Employee testEmployee = testRequest.owner;

         //requestRepository.findById(requestId).get().employee.employeeId ==> returns int not string
        //if request exists
        if (!requestRepository.existsById(requestId)) {
            status = HttpStatus.NOT_FOUND;
        } else {
            //if admin or employeeId is same as request ownerId get comments
            if (signedInRole.contains("administrator") || currentEmployeeId == requestRepository.findById(requestId).get().owner.employeeId) {
                comments = requestRepository.findById(requestId).get().getComment();
                status = HttpStatus.OK;
            } else {
                status = HttpStatus.FORBIDDEN;
            }
        }

        return new ResponseEntity<>(comments, status);
    }

    /*//POST /request/:request_id/comment
    @Operation(summary = "Add comment")
    @PostMapping("/request/{requestId}/add")
    @PreAuthorize("hasAnyRole('user', 'administrator')")//ADMIN / USER protected
    public ResponseEntity<Comment> addComment(@PathVariable Integer requestId, @RequestBody Comment comment, @AuthenticationPrincipal Jwt principal) {


    }

    //GET /request/:request_id/comment/:comment_id
    @Operation(summary = "Get specific comment from a request")
    @GetMapping("/request/{requestId}/{commentId}")
    @PreAuthorize("hasAnyRole('user', 'administrator')")//ADMIN / USER protected
    public ResponseEntity<Comment> getSpecificComment(@PathVariable Integer requestId, @PathVariable Integer commentId, @AuthenticationPrincipal Jwt principal) {


    }

    //PATCH /request/:request_id/comment/:comment_id
    @Operation(summary = "update specific comment from a request")
    @PatchMapping("/request/{requestId}/update/{commentId}")
    @PreAuthorize("hasAnyRole('user', 'administrator')")//ADMIN / USER protected
    public ResponseEntity<Comment> updateComment(@PathVariable Integer requestId, @PathVariable Integer commentId, @RequestBody Comment comment, @AuthenticationPrincipal Jwt principal) {

        //24 HOUR RULE


    }

    //DELETE /request/:request_id/comment/:comment_id
    @Operation(summary = "Delete specific comment from a request")
    @DeleteMapping("/request/{requestId}/delete/{commentId}")
    @PreAuthorize("hasAnyRole('user', 'administrator')")//ADMIN / USER protected
    public ResponseEntity<Comment> deleteComment(@PathVariable  Integer requestId, @PathVariable Integer commentId, @AuthenticationPrincipal Jwt principal) {

        //24 HOUR RULE


    }*/
}
