package com.example.note.controller;

import com.example.note.dto.request.RefreshTokenRequest;
import com.example.note.dto.request.SignInRequest;
import com.example.note.dto.request.SignUpRequest;
import com.example.note.dto.response.SignInResponse;
import com.example.note.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public String signUp(@RequestBody SignUpRequest signUpRequest){
        return authService.signUp(signUpRequest);
    }

    @PostMapping("/signin")
    public SignInResponse signIn(@RequestBody SignInRequest signInRequest){
        return authService.signIn(signInRequest);
    }

    @PostMapping("/refresh-token")
    public SignInResponse refreshAccessToken(@RequestBody RefreshTokenRequest request){
        return authService.refreshAccessToken(request);
    }
}
