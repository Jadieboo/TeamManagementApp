package com.sparta.tma.config;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;



@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfiguration {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(auth -> auth
//                        .anyRequest().authenticated());

        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/admin/**")
                    .hasAuthority("ADMIN");
            auth.requestMatchers("/manager/homepage")
                    .hasAuthority("MANAGER");
            auth.requestMatchers("/employee/**")
                    .hasAuthority("EMPLOYEE");
            auth.requestMatchers("employees")
                    .hasAnyAuthority("MANAGER", "EMPLOYEE");
            auth.anyRequest().authenticated();
        });

        // this was creating issues with not allowing me to access endpoints
//        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.httpBasic(Customizer.withDefaults());

        http.formLogin()
//                .loginPage("/login") // custom login does not work with /loginHandler
                .loginProcessingUrl("/login")
                .successHandler(loginSuccessHandler) // loginSuccessHandler does not redirect
//                .successForwardUrl("/loginHandler") // /loginHandler takes to correct homepage, but I can't access anything else
                .permitAll()
                .and()
                .logout()
                .deleteCookies("JSESSIONID");

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
