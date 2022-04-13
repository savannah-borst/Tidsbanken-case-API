package com.tidsbankencaseapi.Controllers;

import com.tidsbankencaseapi.Models.Comment;
import com.tidsbankencaseapi.Models.Employee;
import com.tidsbankencaseapi.Models.VacationRequest;
import com.tidsbankencaseapi.Repositories.CommentRepository;
import com.tidsbankencaseapi.Repositories.EmployeeRepository;
import com.tidsbankencaseapi.Repositories.VacationRequestRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin("${server.cors.application_origin}")
@RequestMapping("/request/{requestId}")
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
    @GetMapping("/comment")
    @PreAuthorize("hasAnyRole('user', 'administrator')")//ADMIN / USER protected
    public ResponseEntity<List<Comment>> getAllComments(@PathVariable Integer requestId, @AuthenticationPrincipal Jwt principal) {
        //Optionally accepts appropriate query parameters.

        //init
        HttpStatus status;
        VacationRequest request = requestRepository.findById(requestId).get(); //request to get
        List<String> signedInRole = principal.getClaimAsStringList("roles"); //get roles from logged in employee
        String currentEmployeeId = principal.getClaimAsString("sub"); //Logged in employee ID
        List<Comment> comments = new ArrayList<>();

        //if request does not exist return NOT FOUND else get comment
        if (!requestRepository.existsById(requestId)) {
            status = HttpStatus.NOT_FOUND;
        } else {
            //if admin or employeeId is same as request ownerId get comments
            if (signedInRole.contains("administrator") || currentEmployeeId.equals(request.getRequestOwner().getEmployeeId())) {
                //most recent first
                comments = commentRepository.getAllByVacationRequestRequestIdOrderByDateCreatedDesc(requestId);
                status = HttpStatus.OK;
            } else {
                status = HttpStatus.FORBIDDEN;
            }
        }

        return new ResponseEntity<>(comments, status);
    }

    //POST /request/:request_id/comment
    @Operation(summary = "Add comment")
    @PostMapping("/comment/add")
    @PreAuthorize("hasAnyRole('user', 'administrator')")//ADMIN / USER protected
    public ResponseEntity<Comment> addComment(@PathVariable Integer requestId, @RequestBody Comment comment, @AuthenticationPrincipal Jwt principal) {

        //init
        HttpStatus status;
        VacationRequest request = requestRepository.findById(requestId).get(); //request to get
        List<String> signedInRole = principal.getClaimAsStringList("roles"); //get roles from logged in employee
        String currentEmployeeId = principal.getClaimAsString("sub"); //Logged in employee ID
        Date currentTime = new Date();

        //if request does not exist NOT FOUND else if comment does exist BAD REQUEST
        if (!requestRepository.existsById(requestId)) {
            status = HttpStatus.NOT_FOUND;
        } else if (commentRepository.existsById(comment.getCommentId())) {
            status = HttpStatus.BAD_REQUEST;
        } else {
            //if admin or employeeId is same as request ownerId add comment
            if (signedInRole.contains("administrator") || currentEmployeeId.equals(request.getRequestOwner().getEmployeeId()))  {
                //set comment owner, request and createDate
                comment.setCommentOwner(employeeRepository.findById(currentEmployeeId).get());
                comment.setVacationRequest(request);
                comment.setDateCreated(currentTime);
                //save comment
                comment = commentRepository.save(comment);
                status  = HttpStatus.CREATED;
            } else {
                status = HttpStatus.FORBIDDEN;
            }
        }

        return new ResponseEntity<>(comment, status);
    }

    //GET /request/:request_id/comment/:comment_id
    @Operation(summary = "Get specific comment from a request")
    @GetMapping("/comment/{commentId}")
    @PreAuthorize("hasAnyRole('user', 'administrator')")//ADMIN / USER protected
    public ResponseEntity<Comment> getSpecificComment(@PathVariable Integer requestId, @PathVariable Integer commentId, @AuthenticationPrincipal Jwt principal) {

        //init
        HttpStatus status;
        VacationRequest request = requestRepository.findById(requestId).get(); //request to get
        List<String> signedInRole = principal.getClaimAsStringList("roles"); //get roles from logged in employee
        String currentEmployeeId = principal.getClaimAsString("sub"); //Logged in employee ID
        Comment comment = new Comment();

        //if request OR comment does not exist return NOT FOUND
        if (!requestRepository.existsById(requestId) || !commentRepository.existsById(commentId)) {
            status = HttpStatus.NOT_FOUND;
        } else if (requestId == commentRepository.findById(commentId).get().vacationRequest.getRequestId()) {
            //if admin or employeeId is same as request ownerId add comment
            if (signedInRole.contains("administrator") || currentEmployeeId.equals(request.getRequestOwner().getEmployeeId())) {
                comment = commentRepository.findById(commentId).get();
                status  = HttpStatus.OK;
            } else {
                status = HttpStatus.FORBIDDEN;
            }
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(comment, status);

    }

    //PATCH /request/:request_id/comment/:comment_id
    @Operation(summary = "update specific comment from a request")
    @PatchMapping("/comment/update/{commentId}")
    @PreAuthorize("hasAnyRole('user', 'administrator')")//ADMIN / USER protected
    public ResponseEntity<Comment> updateComment(@PathVariable Integer requestId, @PathVariable Integer commentId, @RequestBody Comment comment, @AuthenticationPrincipal Jwt principal) {

        //init
        HttpStatus status;
        Comment returnComment = commentRepository.findById(commentId).get(); //Comment to patch
        String currentEmployeeId = principal.getClaimAsString("sub"); //Logged in employee ID

        //24 hour check
        Date currentTime = new Date();
        long hoursSinceCreate = TimeUnit.HOURS.convert(Math.abs((currentTime.getTime() - returnComment.getDateCreated().getTime())), TimeUnit.MILLISECONDS);

        //if request OR comment does not exist return NOT FOUND
        if (!requestRepository.existsById(requestId) || !commentRepository.existsById(commentId)) {
            status = HttpStatus.NOT_FOUND;
        } else {
            //if logged in employee is comment owner change else AND under 24hours since create else FORBIDDEN
            if (currentEmployeeId.equals(returnComment.commentOwner.getEmployeeId()) && hoursSinceCreate < 24) {

                if (comment.getMessage() != null) {
                    returnComment.setMessage(comment.getMessage());
                }
                //set update time
                returnComment.setDateUpdated(currentTime);

                status = HttpStatus.OK;
            } else {
                status = HttpStatus.FORBIDDEN;
            }
        }

        return new ResponseEntity<>(returnComment, status);
    }

    //DELETE /request/:request_id/comment/:comment_id
    @Operation(summary = "Delete specific comment from a request")
    @DeleteMapping("/comment/delete/{commentId}")
    @PreAuthorize("hasAnyRole('user', 'administrator')")//ADMIN / USER protected
    public ResponseEntity<Comment> deleteComment(@PathVariable  Integer requestId, @PathVariable Integer commentId, @AuthenticationPrincipal Jwt principal) {

        //init
        HttpStatus status;
        Comment comment = commentRepository.findById(commentId).get(); //Comment to Delete
        String currentEmployeeId = principal.getClaimAsString("sub"); //Logged in employee ID

        //24 hour check
        Date currentTime = new Date();
        long hoursSinceCreate = TimeUnit.HOURS.convert(Math.abs((currentTime.getTime() - comment.getDateCreated().getTime())), TimeUnit.MILLISECONDS);

        //if request OR comment does not exist return NOT FOUND
        if (!requestRepository.existsById(requestId) || !commentRepository.existsById(commentId)) {
            status = HttpStatus.NOT_FOUND;
        } else {
            //if logged in employee is comment owner change else AND under 24hours since create else FORBIDDEN
            if (currentEmployeeId.equals(comment.commentOwner.getEmployeeId()) && hoursSinceCreate < 24) {
                commentRepository.deleteById(commentId);
                status = HttpStatus.OK;
            } else {
                status = HttpStatus.FORBIDDEN;
            }
        }

        return new ResponseEntity<>(status);
    }
}
