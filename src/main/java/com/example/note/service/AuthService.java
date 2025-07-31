package com.example.note.service;

import com.example.note.dto.request.SignInRequest;
import com.example.note.dto.request.SignUpRequest;
import com.example.note.dto.response.SignInResponse;
import com.example.note.enums.Role;
import com.example.note.exception.UserAlreadyExist;
import com.example.note.exception.UserNotFoundException;
import com.example.note.model.User;
import com.example.note.repository.UserRepository;
import com.example.note.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public String signUp(SignUpRequest signUpRequest){
        if(userRepository.findByEmail(signUpRequest.email()).isPresent())
            throw new UserAlreadyExist("User Already Exist");

        User user = new User(
                signUpRequest.username(),
                signUpRequest.email(),
                passwordEncoder.encode(signUpRequest.password()),
                Role.USER
        );

        userRepository.save(user);
        return "Sign Up Successful";

    }

    public SignInResponse signIn(SignInRequest signInRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.email(), signInRequest.password())
        );

        User user = userRepository.findByEmail(signInRequest.email())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail());

        return new SignInResponse(token);

    }
}
