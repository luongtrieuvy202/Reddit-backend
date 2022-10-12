package com.programming.technie.springredditclone.controller;

import com.programming.technie.springredditclone.dto.AuthenticationResponse;
import com.programming.technie.springredditclone.dto.LoginRequest;
import com.programming.technie.springredditclone.dto.RegisterRequest;
import com.programming.technie.springredditclone.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        System.out.print(registerRequest);
        return new ResponseEntity<>("User create successfully",OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity verifyAccount(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully",OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        System.out.print(loginRequest);
        return authService.login(loginRequest);
    }



}
