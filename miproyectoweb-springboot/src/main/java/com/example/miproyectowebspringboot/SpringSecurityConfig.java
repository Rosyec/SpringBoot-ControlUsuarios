package com.example.miproyectowebspringboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() throws Exception {

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        
        manager.createUser(
            User
            .withUsername("root")
            .password(passwordEncoder()
            .encode("admin"))
            .roles("ADMIN", "USER")
            .build());

        manager.createUser(User
            .withUsername("ceysor")
            .password(passwordEncoder()
            .encode("12345"))
            .roles("USER")
            .build());
            
            return manager;
        }
    }
    