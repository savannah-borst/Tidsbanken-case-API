package com.tidsbankencaseapi.Controllers;

import com.tidsbankencaseapi.Models.Comment;
import com.tidsbankencaseapi.Models.Employee;
import com.tidsbankencaseapi.Models.Status;
import com.tidsbankencaseapi.Models.VacationRequest;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/vacation_request")
@SecurityRequirement(name = "keycloak_implicit")
public class VacationRequestController {

    private VacationRequestRepository requestRepository;
    private EmployeeRepository employeeRepository;

    public VacationRequestController(VacationRequestRepository requestRepository, EmployeeRepository employeeRepository) {
        this.requestRepository = requestRepository;
        this.employeeRepository = employeeRepository;
    }

    //GET /request
    @Operation(summary = "Get Vacation requests")
    @GetMapping("/")
    @PreAuthorize("hasAnyRole('user', 'administrator')")
    public ResponseEntity<List<VacationRequest>> getListRequest(@AuthenticationPrincipal Jwt principal) {
        //Optionally accepts appropriate query parameters to search and limit responses

        List<VacationRequest> allRequests = requestRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        Employee vacationRequestsOwner = employeeRepository.getById(principal.getSubject());
        ResponseEntity<List<VacationRequest>> response;

        //AUTH
        List<String> signedInRole = principal.getClaimAsStringList("roles");

        if(allRequests.size() == 0){
            status = HttpStatus.NO_CONTENT;
            response = new ResponseEntity<>(status);
        }
        else{
            //Admin can see all requests
            if(signedInRole.contains("administrator")){
                response = new ResponseEntity<>(allRequests, status);
            }
            else{
                List<VacationRequest> filteredRequests = requestRepository
                        .getVacationRequestsByOwnerOrStatus(vacationRequestsOwner, Status.APPROVED);
                response = new ResponseEntity<>(filteredRequests, status);
            }
        }
        return response;
    }

    //POST /newRequest
    @Operation(summary = "Create a new Vacation Request")
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('user', 'administrator')")
    public ResponseEntity<VacationRequest> createVacationRequest(@RequestBody VacationRequest newRequest,
                                                                 @AuthenticationPrincipal Jwt principal) {
        HttpStatus status;
        VacationRequest request = new VacationRequest();
        Employee requestOwner = employeeRepository.getById(principal.getSubject());
        request.setTitle(newRequest.getTitle());
        request.setDateCreated(newRequest.getDateCreated());
        request.setPeriodStart(newRequest.getPeriodStart());
        request.setPeriodEnd(newRequest.getPeriodEnd());
        request.comment = newRequest.comment;

        //employee that made newRequest should be passed as well
        request.setOwner(requestOwner);
        requestRepository.save(newRequest);
        status = HttpStatus.OK;
        return new ResponseEntity<>(request, status);
    }

    //GET /request/:request_id
    @Operation(summary = "Get Vacation request by id")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('user', 'administrator')")
    public ResponseEntity<VacationRequest> getRequest(@PathVariable Integer id,
                                                      @AuthenticationPrincipal Jwt principal) {
        HttpStatus status;

        //AUTH roles
        List<String> signedInRole = principal.getClaimAsStringList("roles");

        if (!requestRepository.existsById(id)) {
            status = HttpStatus.NOT_FOUND;
        } else {
            Optional<VacationRequest> returnRequestRepo = requestRepository.findById(id);
            VacationRequest returnRequest = returnRequestRepo.get();

            //if request is from current employee or user is admin or request is approved return request
            if (signedInRole.contains("administrator")
                    || returnRequest.owner.getEmployeeId().equals(principal.getSubject())
                    || returnRequest.getStatus() == Status.APPROVED) {
                status = HttpStatus.OK;
                return new ResponseEntity<>(returnRequest, status);
            }else{
                status = HttpStatus.FORBIDDEN;
            }
        }
        return new ResponseEntity<>(status);
    }

    //PATCH /request/:request_id
    @Operation(summary = "Update Vacation request")
    @PatchMapping("update/{id}")
    @PreAuthorize("hasAnyRole('user', 'administrator')")
    public ResponseEntity<VacationRequest> updateRequestById(@PathVariable Integer id,
                                                             @RequestBody VacationRequest newRequest,
                                                             @AuthenticationPrincipal Jwt principal) {
        HttpStatus status;

        //AUTH roles
        List<String> signedInRole = principal.getClaimAsStringList("roles");

        if (requestRepository.existsById(id)) {
            Optional<VacationRequest> returnRequestRepo = requestRepository.findById(id);
            VacationRequest returnRequest = returnRequestRepo.get();

            //only request owner can make updates before status is Approved or Denied
            if(returnRequest.owner.getEmployeeId().equals(principal.getSubject())
                    && returnRequest.getStatus().equals(Status.PENDING)) {
                if (newRequest.getTitle() != null) {
                    returnRequest.setTitle(newRequest.getTitle());
                }
                if (newRequest.getPeriodStart() != null) {
                    returnRequest.setPeriodStart(newRequest.getPeriodStart());
                }
                if (newRequest.getPeriodEnd() != null) {
                    returnRequest.setPeriodEnd(newRequest.getPeriodEnd());
                }
                if (newRequest.comment.size() != 0) {
                    for (Comment newComment : newRequest.comment) {
                        returnRequest.comment.add(newComment);
                    }
                }
            }
            //only admin can update request state give HttpStatus.FORBIDDEN as response
            if(signedInRole.contains("administrator")
                        && !returnRequest.owner.getEmployeeId().equals(principal.getSubject()))
            {
                if(newRequest.getStatus() != null){
                    returnRequest.setStatus(newRequest.getStatus());
                }
                // the admin and time of update should be recorded on the request object
                returnRequest.setModerator(employeeRepository.getById(principal.getSubject()));
                returnRequest.setDateUpdated(new Date());
                status = HttpStatus.OK;
            } else{
                status = HttpStatus.FORBIDDEN;
            }
            requestRepository.save(returnRequest);
            status = HttpStatus.NO_CONTENT;
            return new ResponseEntity<>(returnRequest,status);
        }
        else{
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(status);
        }
    }

    //DELETE /request/:request_id
    @Operation(summary = "Delete a Vacation request")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('administrator')")
    public ResponseEntity<String> deleteRequest(@PathVariable Integer id){
        HttpStatus status;
        String response;

        if (!requestRepository.existsById(id)) {
            status = HttpStatus.BAD_REQUEST;
            response = "This request doesn't exist.";
        } else {
            requestRepository.deleteById(id);
            status = HttpStatus.OK;
            response = "Request deleted";
        }

        return new ResponseEntity<>(response, status);
    }
}
