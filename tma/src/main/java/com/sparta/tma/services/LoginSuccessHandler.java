package com.sparta.tma.services;

import com.sparta.tma.entities.AppUser;
import com.sparta.tma.entities.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        AppUser user = (AppUser) authentication.getPrincipal();

        String redirectURL = request.getContextPath();

        if (user.getAuthorities().toString().equals(Role.ADMIN.name())) {
            redirectURL = "/admin_homepage";
        } else if (user.getAuthorities().toString().equals(Role.MANAGER.name())) {
            redirectURL = "/manager_homepage";
        } else if (user.getAuthorities().toString().equals(Role.EMPLOYEE.name())) {
            redirectURL = "/employee_homepage";
        }

        response.sendRedirect(redirectURL);

    }
}
