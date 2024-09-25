package com.dungeon.dungeonserve.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public SecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection
                .csrf(csrf -> csrf.disable())
                // Configure exception handling with the custom entry point
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                )
                // Configure authorization rules
                .authorizeHttpRequests(authorize -> authorize
                        // Permit all OPTIONS requests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // Require authentication for these endpoints
                        .requestMatchers("/api/shop/**", "/api/characters/**").authenticated()
                        // Permit all other requests
                        .anyRequest().permitAll()
                )
                // Configure OAuth2 resource server to use JWT authentication
                .oauth2ResourceServer(oauth2 -> oauth2.jwt())
                // Allow anonymous access
                .anonymous(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri("https://www.googleapis.com/oauth2/v3/certs").build();

        // Define the expected issuer and audience
        String issuer = "https://accounts.google.com";
        String audience = "1003176228232-afv50bp7ju9sut6rjnap1trq5527ai5i.apps.googleusercontent.com";

        // Create validators
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);

        // Combine validators
        OAuth2TokenValidator<Jwt> withIssuerAndAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withIssuerAndAudience);

        return jwtDecoder;
    }
}
