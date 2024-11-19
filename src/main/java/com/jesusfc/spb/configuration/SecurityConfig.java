package com.jesusfc.spb.configuration;

import com.jesusfc.spb.filter.TestSecurityAfterFilter;
import com.jesusfc.spb.filter.TestSecurityBeforeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

/**
 * Author Jesús Fdez. Caraballo
 * jesus.fdez.caraballo@gmail.com
 * Created on nov - 2024
 */
@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults()) //BasicAuthenticationFilter
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated()) //AuthorizationFilter
             //   .addFilterBefore(new TestSecurityBeforeFilter(), AuthorizationFilter.class)
                .addFilterAfter(new TestSecurityAfterFilter(), AuthorizationFilter.class)
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
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
