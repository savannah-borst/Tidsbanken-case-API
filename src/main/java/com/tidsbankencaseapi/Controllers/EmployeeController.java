package com.tidsbankencaseapi.Controllers;

import com.tidsbankencaseapi.Models.Employee;
import com.tidsbankencaseapi.Models.VacationRequest;
import com.tidsbankencaseapi.Repositories.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
@SecurityRequirement(name = "keycloak_implicit")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    //GET /employee
    @Operation(summary = "Get Employee")
    @GetMapping("")
    @PreAuthorize("hasAnyRole({'user', 'administrator'})")//ADMIN / USER protected
    public ResponseEntity<String> getEmployee(@AuthenticationPrincipal Jwt principal) throws URISyntaxException {

        //init
        HttpStatus status;
        String currentEmployeeId = principal.getClaimAsString("sub");
        URI location = new URI(String.format("https://api-tidsbanken-case.herokuapp.com/employee", currentEmployeeId));
        HttpHeaders headers = new HttpHeaders();

        //If employee does not exist BAD REQUEST else SEE OTHER
        if (!employeeRepository.existsById(currentEmployeeId)) {
            status = HttpStatus.BAD_REQUEST;
        } else {
            status = HttpStatus.SEE_OTHER;
            headers.setLocation(location);
            headers.setBearerAuth(String.valueOf(principal));
        }

        return new ResponseEntity<>(headers, status);
    }

    //POST /employee
    @Operation(summary = "Register Employee")
    @PostMapping("/register")
    @PreAuthorize("hasAnyRole({'user', 'administrator'})")//ADMIN / USER protected
    public ResponseEntity<Employee> registerEmployee(@RequestBody Employee employee) {

        //init
        HttpStatus status;

        //always init with admin false
        employee.isAdmin = Boolean.FALSE;

        //If employee does not exist save else BAD REQUEST
        if (!employeeRepository.existsById(employee.employeeId)) {
            employee = employeeRepository.save(employee);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(employee, status);
    }

    //GET /employee/:employee_id
    @Operation(summary = "Get Employee by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole({'user', 'administrator'})")//ADMIN / USER protected
    public ResponseEntity<Employee> getEmployeeId(@PathVariable String id, @AuthenticationPrincipal Jwt principal) {

        //init
        HttpStatus status;
        Employee employee = new Employee();
        List<String> signedInRole = principal.getClaimAsStringList("roles");

        //If employee does not exist NOT FOUND else get employee
        if (!employeeRepository.existsById(id)) {
            status = HttpStatus.NOT_FOUND;
        } else {
            // if admin get everything else just firstname, lastname and pic
            if (signedInRole.contains("administrator")) {
                employee = employeeRepository.findById(id).get();
            } else {
                employee.first_name = employeeRepository.findById(id).get().first_name;
                employee.last_name = employeeRepository.findById(id).get().last_name;
                employee.profilePic = employeeRepository.findById(id).get().profilePic;
            }
            status =HttpStatus.OK;
        }

        return new ResponseEntity<>(employee, status);
    }

    //PATCH /employee/:employee_id
    @Operation(summary = "Update Employee")
    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAnyRole({'user', 'administrator'})")//ADMIN / USER protected
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody Employee employee, @AuthenticationPrincipal Jwt principal) {

        //init
        HttpStatus status;
        Employee returnEmployee = employeeRepository.findById(id).get();
        List<String> signedInRole = principal.getClaimAsStringList("roles");

        //check if pathvariable id equals request employee id
        if (!id.equals(employee.employeeId)) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnEmployee, status);

        } else {

            //if admin is changed by admin change it else return FORBIDDEN.
            if (employee.isAdmin != null)  {
                if (signedInRole.contains("administrator")) {
                    returnEmployee.isAdmin = employee.isAdmin;
                } else  {
                    status =  HttpStatus.FORBIDDEN;
                    return new ResponseEntity<>(returnEmployee, status);
                }
            }

            if (employee.first_name != null) {
                returnEmployee.first_name = employee.first_name;
            }

            if (employee.last_name != null) {
                returnEmployee.last_name = employee.last_name;
            }

            if (employee.emailAddress != null) {
                returnEmployee.emailAddress = employee.emailAddress;
            }

            if (employee.profilePic != null) {
                returnEmployee.profilePic = employee.profilePic;
            }

            employeeRepository.save(returnEmployee);
            status = HttpStatus.NO_CONTENT;
        }

        return new ResponseEntity<>(returnEmployee, status);
    }

    //DELETE /employee/:employee_id
    @Operation(summary = "Delete an Employee")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole({'user', 'administrator'})")//ADMIN / USER protected
    public ResponseEntity<Employee> deleteEmployee(@PathVariable String id, @AuthenticationPrincipal Jwt principal) {

        //init
        HttpStatus status;
        String currentEmployeeId = principal.getClaimAsString("sub");
        List<String> signedInRole = principal.getClaimAsStringList("roles");

        //if employee does not exist BAD REQUEST else delete
        if (!employeeRepository.existsById(id)) {
            status = HttpStatus.BAD_REQUEST;
        } else {
            //if user asking is admin or self delete else FORBIDDEN
            if (signedInRole.contains("administrator") || currentEmployeeId == id) {
                employeeRepository.deleteById(id);
                status = HttpStatus.OK;
            } else {
                status = HttpStatus.FORBIDDEN;
            }
        }

        return new ResponseEntity<>(status);
    }

    //GET /employee/:employee_id/requests
    @Operation(summary = "Get an specific Employee's requests")
    @GetMapping("/{id}/requests")
    @PreAuthorize("hasAnyRole({'user', 'administrator'})")//ADMIN / USER protected
    public ResponseEntity<List<VacationRequest>> getEmployeeRequests(@PathVariable String id, @AuthenticationPrincipal Jwt principal) {
        //Optionally accepts appropriate query parameters to search and limit the number of objects returned.

        //init
        HttpStatus status;
        List<VacationRequest> listRequests = new ArrayList<>();

        //if employee does not exist BAD REQUEST else get list of requests
        if (!employeeRepository.existsById(id)) {
            status = HttpStatus.BAD_REQUEST;
        } else  {
            listRequests = employeeRepository.findById(id).get().getVacationRequests();
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(listRequests, status);
    }
}
