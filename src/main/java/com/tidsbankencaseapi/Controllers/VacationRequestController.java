package com.tidsbankencaseapi.Controllers;

import com.tidsbankencaseapi.Models.VacationRequest;
import com.tidsbankencaseapi.Repositories.VacationRequestRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vacation_request")
public class VacationRequestController {

    @Autowired
    private VacationRequestRepository requestRepository;

    //GET /request
    /*@Operation
    @GetMapping
    public ResponseEntity<List<VacationRequest>> getListRequest() {
        //Optionally accepts appropriate query parameters to search and limit responses

        //AUTH
        //All users may see all approved requests
        //All users may see all own requests (regardless of state)
        //Admin may see all requests (regardless of state)
        List<VacationRequest> requestList =
    }*/

    //POST /request
    @Operation(summary = "Create a New Vacation Request")
    @PostMapping("/create")
    public ResponseEntity<VacationRequest> createVacationRequest(@RequestBody VacationRequest request) {
        HttpStatus status;

        //should have state pending

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

        if (!requestRepository.existsById(id)) {
            status = HttpStatus.NOT_FOUND;
        } else {
            status = HttpStatus.OK;
            request = requestRepository.findById(id).get();
        }
        return new ResponseEntity<>(request, status);
    }

    //PATCH /request/:request_id
    @Operation(summary = "Update vacation request")
    @PatchMapping("update/{id}")
    public ResponseEntity<VacationRequest> updateRequest(@PathVariable Integer id, @RequestBody VacationRequest request) {
        VacationRequest returnRequest = new VacationRequest();
        HttpStatus status;

        //AUTH
        //only request owner can make updates before Approved or Denied
        //only admin can update request without give HttpStatus.FORBIDDEN as response
        // the admin and time of update should be recorded on the request object

        if (!id.equals(request.requestId)) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnRequest, status);
        }
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

        if (!requestRepository.existsById(id)) {
            status = HttpStatus.BAD_REQUEST;
        } else {
            requestRepository.deleteById(id);
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(status);
    }
}
