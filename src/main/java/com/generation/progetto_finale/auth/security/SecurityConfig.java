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
            .requestMatchers(HttpMethod.POST,"/api/products/newProduct").permitAll()
            .requestMatchers(HttpMethod.GET,"/api/products").hasAnyRole("ADMIN", "DIPENDENTE")
            .requestMatchers(HttpMethod.GET,"/api/orders").permitAll()
            .requestMatchers(HttpMethod.PUT,"/api/orders/{id}/updateOrderArrivalDetails").permitAll()
            .requestMatchers(HttpMethod.DELETE,"/api/orders/{id}","/api/orders/deleteLast/{productName}").permitAll()
            .requestMatchers(HttpMethod.DELETE,"/api/profiles/{id}").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/orders/{productId}/addOrder").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/products/newProduct").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/suppliers/addSupplier").permitAll()
            .requestMatchers(HttpMethod.GET, "/profiles/test").permitAll()
            .requestMatchers(HttpMethod.GET, "/profiles/testImage").permitAll()
            .requestMatchers(HttpMethod.GET,"/api/orders/recent").permitAll()
            .requestMatchers(HttpMethod.POST,"/api/profiles/imgupload").permitAll()
            .requestMatchers(HttpMethod.POST,"/api/profiles/imgupload/**", "/api/profiles/newProfile").permitAll()
            .requestMatchers(HttpMethod.GET,"/api/profiles/images/{profileid}").permitAll()
            .requestMatchers(HttpMethod.GET,"/api/profiles/images/**", "/api/profiles/{username}").permitAll()


            // .requestMatchers(HttpMethod.POST).hasRole("ADMIN")
            // .requestMatchers(HttpMethod.GET,"/api/soloperandrea").hasRole("ANDREA")

            .requestMatchers(HttpMethod.GET,"/api/products", "/api/tasks", "/api/categories", "/api/suppliers", "/api/prova", "/api/orders/sendOrders").permitAll()
            .requestMatchers(HttpMethod.GET,"/api/products").hasAnyRole("ADMIN", "DIPENDENTE")
            .requestMatchers(HttpMethod.POST,"/api/tasks/newTask", "/api/products/newProduct").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/communications").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/communications/newCommunication").permitAll()
            .requestMatchers(HttpMethod.DELETE, "/api/communications/{id}").permitAll()
            .requestMatchers(HttpMethod.PUT,"/api/tasks/{id}").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/storedTasks/newStoredTask").permitAll()
            .requestMatchers( "/websocket/**").permitAll()


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
