package com.jesusfc.spb.configuration;

import com.jesusfc.spb.security.filter.JWTAuthenticationFilter;
import com.jesusfc.spb.security.filter.JWTAuthorizationFilter;
import com.jesusfc.spb.security.services.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Author Jesús Fdez. Caraballo
 * jesus.fdez.caraballo@gmail.com
 * Created on nov - 2024
 */
@EnableWebSecurity(debug = true)
@AllArgsConstructor
@Configuration
public class SecurityConfig {

    private final JWTService jwtService;
    //private final ApiSecurityUser apiSecurityUser;

    private final static String[] REQUEST_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/actuator/**",
            "/v1/flight/availability"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        return http
                //    .httpBasic(Customizer.withDefaults()) //BasicAuthenticationFilter
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers(REQUEST_WHITELIST).permitAll()
                                .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                //.addFilterBefore(new SecurityXApiHeaderFilter(apiSecurityUser), JWTAuthorizationFilter.class)
                .addFilter(new JWTAuthenticationFilter(authenticationManager, jwtService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager, jwtService))
                .build();
    }

    /**
     * Configures an in-memory user details service with two users: admin and user.
     *
     * @return an instance of UserDetailsService with predefined users
     */
    @Bean
    public UserDetailsService userDetailsService() {

        // Create two users: admin and user with their respective roles and passwords encoded
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        // Passwords are encoded using BCrypt
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("12345"))
                .roles("ADMIN")
                .build();
        manager.createUser(admin);

        // Passwords are encoded using BCrypt
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("12345"))
                .roles("USER")
                .build();
        manager.createUser(user);

        return manager;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }


}
