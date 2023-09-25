package com.sparta.tma.auth;

import com.sparta.tma.DTOs.AppUserDTO;
import com.sparta.tma.entities.AppUser;
import com.sparta.tma.repositories.AppUserRepository;
import com.sparta.tma.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AppUserRepository appUserRepository;
    private final JwtService jwtService;


    public AuthenticationController(AuthenticationManager authenticationManager, AppUserRepository appUserRepository, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody AppUserDTO request) {
        return ResponseEntity.ok(authenticateRequest(request));
    }

    public JwtResponse authenticateRequest(AppUserDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
                )
        );
        AppUser user = appUserRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return JwtResponse.builder()
                .token(jwtToken)
                .build();
    }
}
