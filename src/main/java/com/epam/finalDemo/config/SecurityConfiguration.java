package com.epam.finalDemo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/api/v1/trainee/register").permitAll()
                                .requestMatchers("/api/v1/trainer/register").permitAll()
                                .requestMatchers("/api/v1/auth/authenticate").permitAll()
                                .requestMatchers("/api/v1/auth/changePassword").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/api/v1/trainingType/all").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/api/v1/training/create").hasRole("ADMIN")
                                .requestMatchers("/api/v1/trainer/changeStatus").hasRole("ADMIN")
                                .requestMatchers("/api/v1/trainer/updateProfile").hasRole("ADMIN")
                                .requestMatchers("/api/v1/trainer/trainings").hasRole("ADMIN")
                                .requestMatchers("/api/v1/trainer/profile").hasRole("ADMIN")
                                .requestMatchers("/api/v1/trainer/delete").hasRole("ADMIN")
                                .requestMatchers("/api/v1/trainee/changeStatus").hasRole("USER")
                                .requestMatchers("/api/v1/trainee/profile").hasRole("USER")
                                .requestMatchers("/api/v1/trainee/trainings").hasRole("USER")
                                .requestMatchers("/api/v1/trainee/updateProfile").hasRole("USER")
                                .requestMatchers("/api/v1/trainee/delete").hasRole("USER")
                                .requestMatchers("/api/v1/trainingType/**").hasRole("USER")
                                .anyRequest().authenticated())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout((logout) -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) ->
                                SecurityContextHolder.clearContext()));
        return http.build();
    }
}
