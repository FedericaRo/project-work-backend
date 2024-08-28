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
            // * Autenticazione
            .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
            // * Utenti
            // ! Creare metodi getAll, Delete, Put per gli Utenti
            /**
             *  ? GetAll per leggerli tutti e avere un quadro della situa
             *  ? Delete per farli eliminare quando necessario agli admin
             *  ? Put per modificare il loro ruolo (solo per admin) o altro se vogliamo
             */
            // * Profili
            .requestMatchers(HttpMethod.GET, "/api/profiles","/api/profiles/profile","/api/profiles/{username}","/api/profiles/images/{profileid}").authenticated()
            .requestMatchers(HttpMethod.POST, "/api/profiles/newProfile","/api/profiles/imgupload/{profileid}").authenticated()
            .requestMatchers(HttpMethod.PUT, "/api/products/").authenticated()
            .requestMatchers(HttpMethod.DELETE, "/api/profiles/{profileId}").authenticated()
            // * Prodotti 
            .requestMatchers(HttpMethod.GET, "/api/products").authenticated()
            .requestMatchers(HttpMethod.POST, "/api/products/newProduct").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/products/{productId}/updateRemainingUnitsQuantity","/api/products/{productId}/updateRemainingPackagesQuantity").authenticated()
            .requestMatchers(HttpMethod.DELETE, "/api/products/{productId}").hasRole("ADMIN")
            // * Ordini
            .requestMatchers(HttpMethod.GET, "/api/orders","/api/orders/recent", "/api/orders/sendOrders").authenticated()
            .requestMatchers(HttpMethod.POST, "/api/orders/{productId}/addOrder").authenticated()
            .requestMatchers(HttpMethod.PUT, "/api/orders/{id}/updateOrderArrivalDetails", "/api/orders/{orderId}/editPackagingQuantity", "/api/orders/{orderId}/editUnitQuantity").authenticated()
            .requestMatchers(HttpMethod.DELETE, "/api/orders/{orderId}","/api/orders/deleteLast/{productName}").authenticated()
            // * Task
            .requestMatchers(HttpMethod.GET, "/api/tasks").authenticated()
            .requestMatchers(HttpMethod.POST, "/api/tasks/newTask").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/tasks/{id}").authenticated()
            // * StoredTask
            .requestMatchers(HttpMethod.GET, "/api/storedTasks").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/storedTasks/newStoredTask").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/storedTasks/{id}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/storedTasks/{id}").hasRole("ADMIN")
            // * Comunicazioni
            .requestMatchers(HttpMethod.GET, "/api/communications","/api/communications/pdf/{communicationid}").authenticated()
            .requestMatchers(HttpMethod.POST, "/api/communications/newCommunication","/api/communications/pdfupload/{communicationid}").authenticated()
            .requestMatchers(HttpMethod.DELETE, "/api/communications/{id}").authenticated()
            // * Categorie
            .requestMatchers(HttpMethod.GET, "/api/categories").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/categories/addCategory").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/categories/{id}").hasRole("ADMIN")
            // * Fornitori
            .requestMatchers(HttpMethod.GET, "/api/suppliers").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/suppliers/addSupplier").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/suppliers/{id}").hasRole("ADMIN")

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
