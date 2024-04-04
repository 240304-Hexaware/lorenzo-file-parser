package com.revature.services;


import com.revature.authentication.AuthenticationRequest;
import com.revature.authentication.AuthenticationResponse;
import com.revature.authentication.RegisterRequest;
import com.revature.entity.User;
import com.revature.enums.UserRole;
import com.revature.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request){
        List<String> errors = validateRegistration(request);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse(null, errors));
        }
        var user =  User.builder()
                .username(request.getUsername()) // Set username from request
                .role(UserRole.USER)
                .email(request.getEmail()) // Set email from request
                .isAccountDisabled(request.getIsAccountDisabled())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        User tempUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);

//        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder().token(jwtToken).build();
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(jwtToken, (String) null, tempUser.getId()); // Set userId
        return ResponseEntity.ok(authenticationResponse);
    }

    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        if (request.getUsername() == null || request.getPassword() == null) {
            throw new IllegalArgumentException("Username and password cannot be null");
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse(null, "Invalid username or password"));
        }

        var user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var jwtToken = jwtService.generateToken(user);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse(jwtToken, (String) null, user.getId()); // Set userId

        return ResponseEntity.ok(authenticationResponse);
    }


    public List<String> validateRegistration(RegisterRequest request) {
        List<String> errors = new ArrayList<>();
        if (request.getUsername() == null || request.getUsername().length() < 8) {
            errors.add("Username must be at least 8 characters long");
        }else if (!request.getUsername().matches("^[a-zA-Z0-9]*$")) {
            errors.add("Username must contain only alphanumeric characters");
        } else if (request.getUsername().contains("  ")) {
            errors.add("Username cannot contain consecutive whitespace characters");
        }

        if (request.getPassword() == null || request.getPassword().length() < 8) {
            errors.add("Password must be at least 8 characters long");
        }
        if (repository.existsByUsername(request.getUsername())) {
            errors.add("Username is already taken");
        }
        if(repository.existsByEmail(request.getEmail())){
            errors.add("Email is already taken");
        }
        return errors;
    }
}
