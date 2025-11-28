package com.medicalcenter.medicalcentersystem.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // 1. Allow public access to home, CSS, login, and new registration pages
                .requestMatchers("/", "/css/**", "/login", "/register", "/save-patient-user").permitAll()
                
                // 2. Create a new rule for patients
                .requestMatchers("/patient/**").hasRole("PATIENT")

                // All other requests require authentication (implicitly ADMINs, as per your setup)
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                // 3. Use a custom success handler for smart redirection
                .successHandler(myAuthenticationSuccessHandler())
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
                // Check the user's roles
                boolean isAdmin = authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
                boolean isPatient = authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_PATIENT"));

                // Redirect based on role
                if (isAdmin) {
                    response.sendRedirect("/dashboard");
                } else if (isPatient) {
                    response.sendRedirect("/patient/dashboard");
                } else {
                    response.sendRedirect("/");
                }
            }
        };
    }
}