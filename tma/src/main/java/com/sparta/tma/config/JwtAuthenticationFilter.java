package com.sparta.tma.config;

import com.sparta.tma.entities.AppUser;
import com.sparta.tma.services.AppUserService;
import com.sparta.tma.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// OncePerRequest = every request needs to be authenticated, inline with stateless
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AppUserService appUserService;

    public JwtAuthenticationFilter(JwtService jwtService, AppUserService appUserService) {
        this.jwtService = jwtService;
        this.appUserService = appUserService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }

        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);

        // check username and user is not authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // get user from database
            AppUser user = (AppUser) appUserService.loadUserByUsername(username);
            //check if user is valid or not
            if (jwtService.isTokenValid(jwt, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                // set auth token with details from request
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // update authentication token
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // pass hand to next filters to be executed
        filterChain.doFilter(request, response);
    }
}
