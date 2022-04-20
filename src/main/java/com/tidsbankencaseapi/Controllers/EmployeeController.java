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
@CrossOrigin("${server.cors.application_origin}")
@RequestMapping("/employee")
@SecurityRequirement(name = "keycloak_implicit")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    //GET /all
    @Operation(summary = "Get all employees")
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('administrator')")
    public ResponseEntity<List<Employee>> getAllEmployees(@AuthenticationPrincipal Jwt principal) {

        List<Employee> allEmployees = employeeRepository.OrderByEmailAddressAsc();
        HttpStatus status = HttpStatus.OK;
        ResponseEntity<List<Employee>> response;

        if(allEmployees.size() == 0){
            status = HttpStatus.NO_CONTENT;
            response = new ResponseEntity<>(status);
        }
        else{
            //Admin can see all employees
            response = new ResponseEntity<>(allEmployees, status);
        }
        return response;
    }

    //GET /employee
    @Operation(summary = "Get Employee")
    @GetMapping("/")
    @PreAuthorize("hasAnyRole({'user', 'administrator'})")//ADMIN / USER protected
    public ResponseEntity<String> getEmployee(@AuthenticationPrincipal Jwt principal) throws URISyntaxException {

        //init
        HttpStatus status;
        String currentEmployeeId = principal.getClaimAsString("sub");
        URI location = new URI(String.format("https://api-tidsbanken-case.herokuapp.com/employee/%s", currentEmployeeId));
        HttpHeaders headers = new HttpHeaders();

        //If employee does not exist BAD REQUEST else SEE OTHER
        if (!employeeRepository.existsById(currentEmployeeId)) {
            status = HttpStatus.NOT_FOUND;
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
    @PreAuthorize("hasAnyRole('administrator')")//ADMIN protected
    public ResponseEntity<Employee> registerEmployee(@RequestBody Employee employee) {

        //init
        HttpStatus status;

        //always init with admin false
        employee.setAdmin(Boolean.FALSE);

        //If employee does not exist save else BAD REQUEST
        if (!employeeRepository.existsById(employee.getEmployeeId())) {
            employee = employeeRepository.save(employee);
            status = HttpStatus.CREATED;
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
            // if admin or same employee as id get everything except id else just firstname, lastname and pic
            if (signedInRole.contains("administrator") || principal.getSubject().equals(id)){
                employee.setEmployeeId(employeeRepository.findById(id).get().getEmployeeId());
                employee.setFirst_name(employeeRepository.findById(id).get().getFirst_name());
                employee.setLast_name(employeeRepository.findById(id).get().getLast_name());
                employee.setEmailAddress(employeeRepository.findById(id).get().getEmailAddress());
                employee.setProfilePic(employeeRepository.findById(id).get().getProfilePic());
                employee.setAdmin(employeeRepository.findById(id).get().getAdmin());
            }
            else {
                employee.setFirst_name(employeeRepository.findById(id).get().getFirst_name());
                employee.setLast_name(employeeRepository.findById(id).get().getLast_name());
                employee.setProfilePic(employeeRepository.findById(id).get().getProfilePic());
            }
            status =HttpStatus.OK;
        }
        return new ResponseEntity<>(employee, status);
    }

    //PATCH /employee/:employee_id
    @Operation(summary = "Update Employee")
    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAnyRole({'user', 'administrator'})")//ADMIN / USER protected
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id,
                                                   @RequestBody Employee employee,
                                                   @AuthenticationPrincipal Jwt principal) {

        //init
        HttpStatus status;
        Employee returnEmployee = employeeRepository.findById(id).get();
        List<String> signedInRole = principal.getClaimAsStringList("roles");

        //check if pathvariable id equals request employee id
        if (!id.equals(employee.getEmployeeId())) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(employee, status);

        } else {

            //if admin is changed by admin change it else return FORBIDDEN.
            if (employee.getAdmin() != null)  {
                if (signedInRole.contains("administrator")) {
                    returnEmployee.setAdmin(employee.getAdmin());
                } else  {
                    status =  HttpStatus.FORBIDDEN;
                    return new ResponseEntity<>(returnEmployee, status);
                }
            }

            if (employee.getFirst_name() != null) {
                returnEmployee.setFirst_name(employee.getFirst_name());
            }

            if (employee.getLast_name() != null) {
                returnEmployee.setLast_name(employee.getLast_name());
            }

            if (employee.getEmailAddress() != null) {
                returnEmployee.setEmailAddress(employee.getEmailAddress());
            }

            if (employee.getProfilePic() != null) {
                returnEmployee.setProfilePic(employee.getProfilePic());
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
            if (signedInRole.contains("administrator") || currentEmployeeId.equals(id)) {
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
    public ResponseEntity<List<VacationRequest>> getEmployeeRequests(@PathVariable String id, @RequestParam long limit, @AuthenticationPrincipal Jwt principal) {
        //Optionally accepts appropriate query parameters to search and limit the number of objects returned.

        //init
        HttpStatus status;
        List<VacationRequest> listRequests = new ArrayList<>();

        //if employee does not exist BAD REQUEST else get list of requests
        if (!employeeRepository.existsById(id)) {
            status = HttpStatus.NOT_FOUND;
        } else {
            if(!principal.getSubject().equals(id)){
                status = HttpStatus.FORBIDDEN;
            }
            else{
                listRequests = employeeRepository.findById(id).get().getVacationRequests();
                status = HttpStatus.OK;
            }
        }
        return new ResponseEntity<>(listRequests, status);
    }
}
