package com.tidsbankencaseapi.Controllers;

import com.tidsbankencaseapi.Models.Employee;
import com.tidsbankencaseapi.Models.Status;
import com.tidsbankencaseapi.Models.VacationRequest;
import com.tidsbankencaseapi.Repositories.VacationRequestRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vacation_request")
public class VacationRequestController {

    @Autowired
    private VacationRequestRepository requestRepository;

    //GET /request
    @Operation
    @GetMapping("/")

    public ResponseEntity<List<VacationRequest>> getListRequest() {
        //Optionally accepts appropriate query parameters to search and limit responses
        List<VacationRequest> allRequests = requestRepository.findAll();
        List<VacationRequest> shownRequests = new ArrayList<>();
        HttpStatus status;

        //AUTH
        //All users may see all approved requests
        //All users may see all own requests (regardless of state)
        //Admin may see all requests (regardless of state)
        // MAP!!!
        /*if (logged in employee != isAdmin) {
            for (int i = 0; i < allRequests.size(); i++) {
                if (employee logged in === allRequests.get(i).employee || allRequests.get(i).status === Status.APPROVED) {
                    shownRequests.add(allRequests.get(i));
                }
            }*/
            status = HttpStatus.OK;
            return new ResponseEntity<>(shownRequests, status);
       /* } else {
            status = HttpStatus.OK;
            return new ResponseEntity<>(allRequests, status)
        }*/
    }

    //POST /request
    @Operation(summary = "Create a New Vacation Request")
    @PostMapping("/create")
    public ResponseEntity<VacationRequest> createVacationRequest(@RequestBody VacationRequest request) {
        HttpStatus status;

        //should have state pending
        request.status = Status.PENDING;
        //employee that made request should be passed as well

        request = requestRepository.save(request);
        status =HttpStatus.OK;

        return new ResponseEntity<>(request, status);
    }

    //GET /request/:request_id
    @Operation(summary = "Get request by id")
    @GetMapping("/{id}")
    public ResponseEntity<VacationRequest> getRequest(@PathVariable Integer id) {
        VacationRequest request = new VacationRequest();
        HttpStatus status;

        //AUTH
        //if request = NOT from current employee or admin & request is not approved return HttpStatus.FORBIDDEN
        //see in else statement

        if (!requestRepository.existsById(id)) {
            status = HttpStatus.NOT_FOUND;
        } else {
            //if (employee logged in === request.employee || employee logged in.isAdmin || request.status === Status.APPROVED) {
                status = HttpStatus.OK;
                request = requestRepository.findById(id).get();
            /*} else {
                status = HttpStatus.FORBIDDEN
            }*/
        }
        return new ResponseEntity<>(request, status);
    }

    //PATCH /request/:request_id
    @Operation(summary = "Update vacation request")
    @PatchMapping("update/{id}")
    public ResponseEntity<VacationRequest> updateRequest(@PathVariable Integer id, @RequestBody VacationRequest request) {
        VacationRequest returnRequest = requestRepository.findById(id).get();
        HttpStatus status;

        //AUTH
        // the admin and time of update should be recorded on the request object

        if (!id.equals(request.requestId)) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnRequest, status);
        }
        //only request owner can make updates before Approved or Denied
        /*if (employee logged in === request.employee && request.status === Status.PENDING) {
            returnRequest = requestRepository.save(request);
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.FORBIDDEN
        }*/

        //only admin can update request state without give HttpStatus.FORBIDDEN as response
        /*if (returnRequest.status != request.status && employee logged in.isAdmin){
            returnRequest = requestRepository.save(request);
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.FORBIDDEN
        }*/

        returnRequest = requestRepository.save(request);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnRequest,status);
    }

    //DELETE /request/:request_id
    @Operation(summary = "Delete a vacation request")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<VacationRequest> deleteRequest(@PathVariable Integer id){
        HttpStatus status;

        //AUTH
        //admin only
        /*if (employee logged in.isAdmin) {

        } else {
            status = HttpStatus.FORBIDDEN
        }*/

        if (!requestRepository.existsById(id)) {
            status = HttpStatus.BAD_REQUEST;
        } else {
            requestRepository.deleteById(id);
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(status);
    }
}
