package com.sparta.tma.config;

import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        logger.info("in redirection");

        String userAuth = ((AppUser) authentication.getPrincipal()).getRole().toString();
        logger.info("Principal name: " + authentication.getName());
        logger.info("User role: " + userAuth);

        String redirectURL = request.getContextPath();
        logger.info("Context Path: " + redirectURL);

        if (userAuth.equals(Role.ADMIN.name())) {
            redirectURL = "/admin/homepage";
            logger.info("user authority is role " + userAuth + " > redirection:" + redirectURL);

        } else if (userAuth.equals(Role.MANAGER.name())) {
            redirectURL = "/manager/homepage";
            logger.info("user authority is role " + userAuth + " > redirection:" + redirectURL);

        } else if (userAuth.equals(Role.EMPLOYEE.name())) {
            redirectURL = "/employee/homepage";
            logger.info("user authority is role " + userAuth + " > redirection:" + redirectURL);

        } else {
            logger.info("Redirection failed");
        }

        response.sendRedirect(redirectURL);

    }
}
