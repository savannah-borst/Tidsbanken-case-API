package com.tidsbankencaseapi.Controllers;

import com.tidsbankencaseapi.Models.IneligiblePeriod;
import com.tidsbankencaseapi.Repositories.IneligiblePeriodRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("${server.cors.application_origin}")
@RequestMapping("/ineligibleperiod")
@SecurityRequirement(name = "keycloak_implicit")
public class IneligiblePeriodController {

    private final IneligiblePeriodRepository ineligiblePeriodRepository;

    public IneligiblePeriodController(IneligiblePeriodRepository ineligiblePeriodRepository) {
        this.ineligiblePeriodRepository = ineligiblePeriodRepository;

    }

    //GET /ineligible
    @Operation(summary = "Get all ineligible periods")
    @GetMapping("")
    @PreAuthorize("hasAnyRole('user', 'administrator')")
    public ResponseEntity<List<IneligiblePeriod>> getIneligiblePeriods(@AuthenticationPrincipal Jwt principal){
        HttpStatus status;

        //Get ineligible periods ordered by start period
        Optional<List<IneligiblePeriod>> ineligiblePeriodsListRepo =
                ineligiblePeriodRepository.OrderByPeriodStartAsc();

        if(!ineligiblePeriodsListRepo.isEmpty()){
            status = HttpStatus.OK;
            return new ResponseEntity<>(ineligiblePeriodsListRepo.get(), status);
        }else{
            status = HttpStatus.NO_CONTENT;
            return new ResponseEntity<>(status);
        }

    }

    //POST /ineligible
    @Operation(summary = "Create a new ineligible period")
    @PostMapping("")
    @PreAuthorize("hasAnyRole('administrator')")
    public ResponseEntity<IneligiblePeriod> createIneligiblePeriod(
            @RequestBody IneligiblePeriod newIneligiblePeriod,
            @AuthenticationPrincipal Jwt principal){

        HttpStatus status;

        if(!ineligiblePeriodRepository.existsById(newIneligiblePeriod.getIneligiblePeriodId())){
            ineligiblePeriodRepository.save(newIneligiblePeriod);
            status = HttpStatus.CREATED;
            return new ResponseEntity<>(newIneligiblePeriod, status);
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

    //GET /ineligible/:ip_id
    @Operation(summary = "Get ineligible period by ip_id")
    @GetMapping("/{ip_id}")
    @PreAuthorize("hasAnyRole('user','admin')")
    public ResponseEntity<IneligiblePeriod> getIneligiblePeriodById(
            Integer ip_id, @AuthenticationPrincipal Jwt principal){

        HttpStatus status;

        if(!ineligiblePeriodRepository.existsById(ip_id)){
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        } else{
            Optional<IneligiblePeriod> ineligiblePeriodRepo =
                    ineligiblePeriodRepository.findById(ip_id);
            IneligiblePeriod ineligiblePeriod = ineligiblePeriodRepo.get();
            status = HttpStatus.OK;
            return new ResponseEntity<>(ineligiblePeriod, status);
        }

    }

    //PATCH /ineligible/:ip_id
    @Operation(summary = "Update ineligible period by ip_id")
    @PatchMapping("/{ip_id}")
    @PreAuthorize("hasAnyRole('administrator')")
    public ResponseEntity<IneligiblePeriod> updateIneligiblePeriod(Integer ip_id,
            @RequestBody IneligiblePeriod newIneligiblePeriod,
            @AuthenticationPrincipal Jwt principal){

        HttpStatus status;

        //Check if ineligible period exists
        if(!ineligiblePeriodRepository.existsById(ip_id)){
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        } else{
            Optional<IneligiblePeriod> ineligiblePeriodRepo =
                    ineligiblePeriodRepository.findById(ip_id);
            IneligiblePeriod ineligiblePeriod = ineligiblePeriodRepo.get();
            if(newIneligiblePeriod.getPeriodStart() != null){
                ineligiblePeriod.setPeriodStart(newIneligiblePeriod.getPeriodStart());
            }
            if(newIneligiblePeriod.getPeriodEnd() != null){
                ineligiblePeriod.setPeriodEnd(newIneligiblePeriod.getPeriodEnd());
            }
            ineligiblePeriodRepository.save(ineligiblePeriod);
            status = HttpStatus.CREATED;
            return new ResponseEntity<>(ineligiblePeriod, status);
        }
    }

    //DELETE /ineligible/:ip_id
    @Operation(summary = "Delete ineligible period by ip_id")
    @DeleteMapping("{ip_id}")
    @PreAuthorize("hasAnyRole('administrator')")
    public ResponseEntity<String> deleteIneligiblePeriodById(
            Integer ip_id, @AuthenticationPrincipal Jwt principal){

        HttpStatus status;

        //Check if ineligible period exists
        if(!ineligiblePeriodRepository.existsById(ip_id)){
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>("Ineligible period not found", status);
        } else{
            ineligiblePeriodRepository.deleteById(ip_id);
            status = HttpStatus.OK;
            return new ResponseEntity<>("Ineligible period deleted", status);
        }
    }
}
