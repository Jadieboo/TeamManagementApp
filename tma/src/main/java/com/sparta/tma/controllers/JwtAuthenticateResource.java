//package com.sparta.tma.controllers;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.oauth2.jwt.JwtClaimsSet;
//import org.springframework.security.oauth2.jwt.JwtEncoder;
//import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.Instant;
//import java.util.stream.Collectors;
//
//@RestController
//public class JwtAuthenticateResource {
//
//    // TODO: look into how to automatically pass token to REST API methods
//    // TODO: research how to validate tokens for each users authority/role
//
//    record JwtResponse(String token) {}
//    private final JwtEncoder encoder;
//
//    public JwtAuthenticateResource(JwtEncoder encoder) {
//        this.encoder = encoder;
//    }
//
//    @PostMapping("/authenticate")
//    public JwtResponse authenticate(Authentication authentication) {
//        return new JwtResponse(createToken(authentication));
//    }
//
//    private String createToken(Authentication authentication) {
//        JwtClaimsSet claims = JwtClaimsSet.builder()
//                .issuer("self")
//                .issuedAt(Instant.now())
//                .expiresAt(Instant.now().plusSeconds(60 * 15))
//                .subject(authentication.getName())
//                .claim("scope", createScope(authentication))
//                .build();
//
//        JwtEncoderParameters parameters = JwtEncoderParameters.from(claims);
//
//        return encoder.encode(parameters).getTokenValue();
//    }
//
//    private String createScope(Authentication authentication) {
//        return authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(" "));
//    }
//
//}
//
//
//
