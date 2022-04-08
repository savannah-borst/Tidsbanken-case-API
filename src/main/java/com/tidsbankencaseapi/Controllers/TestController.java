package com.tidsbankencaseapi.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String sayHello(){
        return "Hello Tidsbanken";
    }

    @GetMapping("/employee")
    public String helloEmployee(){
        return "Hello Employee";
    }

    @GetMapping("/employee/1")
    public String helloEmployeeOne(){
        return "Hello Employee with id 1";
    }
}
