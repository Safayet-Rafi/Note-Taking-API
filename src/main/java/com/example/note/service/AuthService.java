package com.example.note.service;

import com.example.note.dto.request.RefreshTokenRequest;
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
                Role.USER,
                null
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

        String accessToken = jwtUtil.generateAccessToken(user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new SignInResponse(accessToken, refreshToken);

    }

    public SignInResponse refreshAccessToken(RefreshTokenRequest request){
        String providedRefreshToken = request.refreshToken();
        String email = jwtUtil.extractUsername(providedRefreshToken);

        User user = userRepository.findByEmail(email)
                .orElseThrow( () -> new UserNotFoundException("User not found"));

        String storedRefreshToken = user.getRefreshToken();

        if(!providedRefreshToken.equals(storedRefreshToken) || !jwtUtil.isTokenValid(providedRefreshToken, email)){
            throw new RuntimeException("Token is not valid");
        }

        String newAccessToken = jwtUtil.generateAccessToken(email);

        return new SignInResponse(newAccessToken, storedRefreshToken);
    }
}
