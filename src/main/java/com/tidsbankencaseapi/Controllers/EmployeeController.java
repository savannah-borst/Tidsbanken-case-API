package com.tidsbankencaseapi.Controllers;

import com.tidsbankencaseapi.Models.Employee;
import com.tidsbankencaseapi.Models.VacationRequest;
import com.tidsbankencaseapi.Repositories.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    //GET /employee
    @Operation(summary = "Get Employee")
    @GetMapping("")
    public ResponseEntity<Employee> getEmployee() {
        HttpStatus status;

        //AUTH
        //get logged in employee / Can we get their id as well?

        if (!employeeRepository.existsById(id) {
            status = HttpStatus.BAD_REQUEST;
        } else {
            status = HttpStatus.SEE_OTHER;
            //location
        }
    }

    //POST /employee
    @Operation(summary = "Register Employee")
    @PostMapping("/register")
    public ResponseEntity<Employee> registerEmployee(@RequestBody Employee employee) {
        HttpStatus status;
        //AUTH
        //add employee in keycloak
        if (logged in.isAdmin) {
            if (!employeeRepository.existsById(employee.employeeId)) {
                employee = employeeRepository.save(employee);
                status = HttpStatus.OK;
            } else {
                status = HttpStatus.BAD_REQUEST;
            }
        } else  {
            status = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity<>(employee, status);
    }

    //GET /employee/:employee_id
    @Operation(summary = "Get Employee by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeId(@PathVariable Integer id) {
        Employee employee = new Employee();
        HttpStatus status;
        //AUTH
        //if user should return only pic and name


        if (!employeeRepository.existsById(id)) {
            status = HttpStatus.NOT_FOUND;
        } else {
            status = HttpStatus.OK;
            employee = employeeRepository.findById(id).get();
        }

        return new ResponseEntity<>(employee, status);
    }

    //PATCH /employee/:employee_id
    @Operation(summary = "Update Employee")
    @PatchMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        //AUTH
        //update of password returns bad request
        //only admin can change isAdmin all other unauth attempts respond with 403 Forbidden
        Employee returnEmployee = employeeRepository.findById(id).get();
        HttpStatus status;

        if (!id.equals(employee.employeeId)) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnEmployee, status);
        }
        if (returnEmployee.isAdmin != employee.isAdmin && logged in != isAdmin) {
            status = HttpStatus.FORBIDDEN
        } else {

        }
        returnEmployee = employeeRepository.save(employee);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnEmployee, status);
    }

    //DELETE /employee/:employee_id
    @Operation(summary = "Delete an Employee")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable Integer id) {
        HttpStatus status;
        //AUTH
        //only self or admin can delete

        if (!employeeRepository.existsById(id)) {
            status = HttpStatus.BAD_REQUEST;
        } else {
            employeeRepository.deleteById(id);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }

    //GET /employee/:employee_id/requests
    @Operation(summary = "Get an specific Employee's requests")
    @GetMapping("/{id}/requests")
    public ResponseEntity<List<VacationRequest>> getEmployeeRequests(@PathVariable Integer id) {
        //Optionally accepts appropriate query parameters to search and limit the number of objects returned.

        HttpStatus status;
        List<VacationRequest> listRequests = new ArrayList<>();

        if (!employeeRepository.existsById(id)) {
            status = HttpStatus.BAD_REQUEST;
        } else  {
            Employee employee = employeeRepository.findById(id).get();
            listRequests = employee.getVacationRequests();
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(listRequests, status);
    }

    //POST /employee/:employee_id/update_password
    /*@Operation(summary = "Change Password")
    @PostMapping("/{id}/update_password")
    public ResponseEntity<Employee> updatePassword(@PathVariable Integer id, @RequestBody String Password) {
        //AUTH
        //Send new password to keycloak
    }*/

}
