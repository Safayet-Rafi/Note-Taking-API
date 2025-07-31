package com.example.note.service;

import com.example.note.exception.UserNotFoundException;
import com.example.note.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException{
        return userRepository.findByEmail(email)
                .orElseThrow( () -> new UserNotFoundException("User Not Found"));
    }
}
