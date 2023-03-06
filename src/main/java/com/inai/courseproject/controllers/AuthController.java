package com.inai.courseproject.controllers;

import com.inai.courseproject.dto.CredentialsDto;
import com.inai.courseproject.dto.UserDto;
import com.inai.courseproject.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/index/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public void register(@RequestBody UserDto userDto) {
        authService.register(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<HttpStatus> login(@RequestBody CredentialsDto credentialsDto) {
        authService.login(credentialsDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
