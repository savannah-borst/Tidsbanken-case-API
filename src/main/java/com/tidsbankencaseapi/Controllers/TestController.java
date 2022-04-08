package com.tidsbankencaseapi.Controllers;

import com.tidsbankencaseapi.Models.CommonResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    //Protected method to inspect JWT
    @GetMapping("/inspect")
    @PreAuthorize("hasAuthority('GROUP_admin')")
    public ResponseEntity<CommonResponse<Object>> inspect(
            @AuthenticationPrincipal Jwt principal
    ) {
        return ResponseEntity
                .ok(new CommonResponse<>(principal.getClaims().get("given_name")));
    }

}
