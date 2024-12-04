package com.example.Api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @SuppressWarnings("removal")
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Disable CSRF for simplicity (not recommended for production)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Allow all endpoints without authentication
            )
            .headers().frameOptions().disable() // Allow using H2 console or similar
            .and()
            .httpBasic().disable() // Disable basic authentication
            .formLogin().disable(); // Disable form-based login

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Still available for encoding passwords
    }
}
