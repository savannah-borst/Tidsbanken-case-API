package com.tidsbankencaseapi.Controllers;

import com.tidsbankencaseapi.Models.VacationRequest;
import com.tidsbankencaseapi.Repositories.VacationRequestRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vacation_request")
public class VacationRequestController {

    @Autowired
    private VacationRequestRepository requestRepository;

    //GET /request

    //POST /request
    @Operation(summary = "Create a New Vacation Request")
    @PostMapping("/create")
    public ResponseEntity<VacationRequest> createVacationRequest(@RequestBody VacationRequest request) {
        HttpStatus status;

        request = requestRepository.save(request);
        status =HttpStatus.OK;

        return new ResponseEntity<>(request, status);
    }

    //GET /request/:request_id

    //PATCH /request/:request_id

    //DELETE /request/:request_id
}
