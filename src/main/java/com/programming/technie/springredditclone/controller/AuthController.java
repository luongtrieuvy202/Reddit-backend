package com.programming.technie.springredditclone.controller;

import static org.springframework.http.HttpStatus.OK;

import javax.validation.Valid;

import com.programming.technie.springredditclone.dto.AuthenticationResponse;
import com.programming.technie.springredditclone.dto.LoginRequest;
import com.programming.technie.springredditclone.dto.RefreshTokenRequest;
import com.programming.technie.springredditclone.dto.RegisterRequest;
import com.programming.technie.springredditclone.service.AuthService;
import com.programming.technie.springredditclone.service.RefreshTokenService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    
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
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh token deleted successfully");
    }



}
