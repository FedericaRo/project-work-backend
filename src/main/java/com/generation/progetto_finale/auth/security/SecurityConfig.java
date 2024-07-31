package com.generation.progetto_finale.auth.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthEntryPoint authEntryPoint;


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http
        .csrf(csrf -> csrf.disable())
        .exceptionHandling(handling -> handling.authenticationEntryPoint(authEntryPoint))
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            authorize -> 
            authorize
            .requestMatchers("/api/auth/**","/swagger-ui/**","/api/v3/api-docs/**").permitAll()
                    // .requestMatchers(HttpMethod.GET, "/api/products").hasRole("DIPENDENTE")
            .requestMatchers(HttpMethod.GET,"/api/products").hasAnyRole("ADMIN", "DIPENDENTE")
            .requestMatchers(HttpMethod.GET,"/api/orders").hasAnyRole("ADMIN", "DIPENDENTE")
            .requestMatchers(HttpMethod.PUT,"/api/orders/{id}/changeArrivedStatus").hasAnyRole("ADMIN", "DIPENDENTE")
            .requestMatchers(HttpMethod.DELETE,"/api/orders/{id}").hasAnyRole("ADMIN", "DIPENDENTE")
            // .requestMatchers(HttpMethod.POST).hasRole("ADMIN")
            // .requestMatchers(HttpMethod.GET,"/api/soloperandrea").hasRole("ANDREA")

            .requestMatchers(HttpMethod.GET,"/api/products", "/api/tasks").permitAll()
            .requestMatchers(HttpMethod.GET,"/api/products").hasAnyRole("ADMIN", "DIPENDENTE")
            .requestMatchers(HttpMethod.POST,"/api/tasks/newTask").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/communications").permitAll()
            .requestMatchers(HttpMethod.DELETE, "/api/communications/{id}").permitAll()
            .requestMatchers(HttpMethod.PUT,"/api/tasks/{id}").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/storedTasks/newStoredTask").permitAll()

            .anyRequest().authenticated()
        )
        .httpBasic(withDefaults());

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }
}
