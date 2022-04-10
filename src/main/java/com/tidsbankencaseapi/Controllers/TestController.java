package com.tidsbankencaseapi.Controllers;

import com.tidsbankencaseapi.Models.CommonResponse;
import com.tidsbankencaseapi.Models.Employee;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@RestController
@SecurityRequirement(name = "keycloak_implicit")
public class TestController {

    @GetMapping("/")
    public String sayHello(){
        return "Hello Tidsbanken";
    }

    //Public without authentication
    @GetMapping("/employee")
    public String helloEmployee(){
        return "Hello unprotected employee";
    }

    //Protected with authentication
    @GetMapping("/employee/protected/admin")
    @PreAuthorize("hasAuthority('GROUP_admin')")
    public String helloProtectedAdmin(){
        return "Hello protected admin";
    }


    //Protected with authentication
    @GetMapping("/employee/protected/user")
    @PreAuthorize("hasAuthority('GROUP_user')")
    public String helloProtectedDefaultUser(){
        return "Hello protected default user";
    }

    //Protected with authentication
    @GetMapping("/employee/protected/user/role")
    @PreAuthorize("hasRole('user')")
    public String helloProtectedDefaultUserRole(){
        return "Has role user";
    }

    //Protected method to inspect JWT
    @GetMapping("/inspect")
    @PreAuthorize("hasAuthority('GROUP_admin')")
    public ResponseEntity<CommonResponse<Object>> inspect(@AuthenticationPrincipal Jwt principal)
    {
        return ResponseEntity
                .ok(new CommonResponse<>(principal.getClaims().get("given_name")));
    }

    //Show the mapped information retrieved from the Jwt
    @GetMapping("/userInfo")
    public Map<String, Object> getUserInfo(@AuthenticationPrincipal Jwt principal){
       Map<String, String> userMap = new Hashtable<String, String>();
       userMap.put("user_uuid", principal.getClaimAsString("sub"));
        userMap.put("user_name", principal.getClaimAsString("preferred_username"));
        userMap.put("email", principal.getClaimAsString("email"));
        userMap.put("first_name", principal.getClaimAsString("given_name"));
        userMap.put("last_name", principal.getClaimAsString("family_name"));
        userMap.put("roles", String.valueOf(principal.getClaimAsString("roles")));
        return Collections.unmodifiableMap(userMap);
    }

    //Returns the entire principal object
    @GetMapping("/principal")
    public Principal getUser(Principal user){
        return user;
    }

//    @PostMapping
//    public ResponseEntity<Employee>addNewEmployee(@AuthenticationPrincipal Jwt principal){
//        //check if employee exists by email
//        //service.checkIfEmployeeExists(principal.getClaimAsString("email")
//        //if true return bad request
//        //if false create new employee based on principal
//    }

}
