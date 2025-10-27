package com.vornicu.user_service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;



    public AuthenticationResponse register(RegistrationRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already registered!");
        }
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .activationToken(UUID.randomUUID().toString())
                .enabled(false)
                .build();

        userRepository.save(user);

        return AuthenticationResponse.builder()
                .message("User registered successfully. Check email to activate your account")
                .build();
    }

    public AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found!"));
        if(!user.isEnabled()){
            throw new RuntimeException("Account not activated!");
        }

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void activateAccount(String token){
        Optional<User> optionalUser = userRepository.findByActivationToken(token);
        User user = optionalUser.orElseThrow(
                () -> new RuntimeException("Invalid activation token!")
        );
        user.setEnabled(true);
        user.setActivationToken(null);
        userRepository.save(user);
    }
}
