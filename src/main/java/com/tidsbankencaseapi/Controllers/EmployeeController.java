package com.tidsbankencaseapi.Controllers;

import com.tidsbankencaseapi.Models.Employee;
import com.tidsbankencaseapi.Repositories.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    //GET /employee

    //POST /employee
    @Operation(summary = "Register Employee")
    @PostMapping("/register")
    public ResponseEntity<Employee> registerEmployee(@RequestBody Employee employee) {
        HttpStatus status;

        if (employeeRepository.existsById(employee.employeeId)) {
            status = HttpStatus.BAD_REQUEST;
        } else {
            employee = employeeRepository.save(employee);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(employee, status);
    }

    //GET /employee/:employee_id
    @Operation(summary = "Get Employee by ID")
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Integer id) {
        Employee employee = new Employee();
        HttpStatus status;
        //if user should return only pic and name

        if (employeeRepository.existsById(id)) {
            status = HttpStatus.OK;
            employee = employeeRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(employee, status);
    }

    //PATCH /employee/:employee_id

    //DELETE /employee/:employee_id

    //GET /employee/:employee_id/requests

    //POST /employee/:employee_id/update_password


}
