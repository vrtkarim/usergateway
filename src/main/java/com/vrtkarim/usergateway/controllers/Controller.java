package com.vrtkarim.usergateway.controllers;

import com.vrtkarim.usergateway.dto.JwtAuthenticationResponse;
import com.vrtkarim.usergateway.dto.SignInRequest;
import com.vrtkarim.usergateway.dto.SignUpRequest;
import com.vrtkarim.usergateway.services.AuthenticationService;
import com.vrtkarim.usergateway.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class Controller {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    @PostMapping("/signin")
    public JwtAuthenticationResponse signin(@RequestBody SignInRequest signInRequest) {
        return  authenticationService.signin(signInRequest);
    }
    @PostMapping("/signup")
    public JwtAuthenticationResponse signup(@RequestBody SignUpRequest signUpRequest) {
        return authenticationService.signup(signUpRequest);
    }
    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint() {
        return new ResponseEntity<>("hello tous le monde", HttpStatus.OK);
    }
    @GetMapping("/authenticated")
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<String> authenticatedEndpoint() {
        return new ResponseEntity<>("hello authenticated user ", HttpStatus.OK);
    }
}
