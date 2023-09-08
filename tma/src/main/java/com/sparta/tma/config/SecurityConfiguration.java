package com.sparta.tma.config;

import com.sparta.tma.entities.Role;
import com.sparta.tma.services.LoginSuccessHandler;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(auth -> auth
//                        .anyRequest().authenticated());

        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("admin/**")
                    .hasAuthority(Role.ADMIN.name());
            auth.requestMatchers("/manager/**")
                    .hasAuthority(Role.MANAGER.name());
            auth.requestMatchers("/employee/**")
                    .hasAuthority(Role.EMPLOYEE.name());
            auth.requestMatchers("employees")
                    .hasAnyAuthority(Role.MANAGER.name(), Role.EMPLOYEE.name());
        });

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.httpBasic(Customizer.withDefaults());
        http.formLogin()
                .loginPage("/loginPage")
                .successHandler(loginSuccessHandler)
                .permitAll();

//        Set<String> authorities = authentication.getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toSet());
//
//        if (authorities.contains("ROLE_ADMIN")) {
//            response.sendRedirect("/admin_homepage");
//        } else if (authorities.contains("ROLE_MANAGER")) {
//            response.sendRedirect("/manager_homepage");
//        } else if (authorities.contains("ROLE_EMPLOYEE")) {
//            response.sendRedirect("/employee_homepage");
//        } else {
//            // Handle other authorities or redirect to a default page
//            response.sendRedirect("/default_page");
//        }
//    })

        http.csrf((AbstractHttpConfigurer::disable));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(UserDetailsService detailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(detailsService);
        return new ProviderManager(provider);
    }

}
