package com.sparta.tma.config;

import lombok.AllArgsConstructor;
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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/login")
                            .permitAll();
            auth.requestMatchers("/admin/**")
                    .hasAuthority("ADMIN");
            auth.requestMatchers("/manager/**")
                    .hasAuthority("MANAGER");
            auth.requestMatchers("/employee/**")
                    .hasAuthority("EMPLOYEE");
            auth.requestMatchers("employees")
                    .hasAnyAuthority("MANAGER", "EMPLOYEE");
            auth.anyRequest().authenticated();
        })
        // Form-Based Authentication
        .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/perform_login")
                        .successHandler(loginSuccessHandler()))
        .logout(logout -> logout
                .logoutUrl("/logout")
                        .deleteCookies("JSESSIONID"))
        // API Authentication
        .httpBasic(Customizer.withDefaults())
        .build();


//        http.csrf((AbstractHttpConfigurer::disable));

        // OAuth2 Resource Server
//        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        // this was creating issues with not allowing me to access endpoints
//        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

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

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }
//
//    // ------ JWT config ------
//
//    // Key Pair
//    @Bean
//    public KeyPair keyPair() {
//        try {
//            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//            keyPairGenerator.initialize(2048);
//
//            return keyPairGenerator.generateKeyPair();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // RSA Key object
//    @Bean
//    public RSAKey rsaKey(KeyPair keyPair) {
//        return new RSAKey
//                .Builder((RSAPublicKey) keyPair.getPublic())
//                .privateKey(keyPair.getPrivate())
//                .keyID(UUID.randomUUID().toString())
//                .build();
//    }
//
//    // JWKSource
//    @Bean
//    public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
//        JWKSet jwkSet = new JWKSet(rsaKey);
//
//        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
//    }
//
//    // JWT Decoder
//    @Bean
//    public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
//        return NimbusJwtDecoder
//                .withPublicKey(rsaKey.toRSAPublicKey())
//                .build();
//    }
//
//    // JWT Encoder
//    @Bean
//    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
//        return new NimbusJwtEncoder(jwkSource);
//    }
//


}
