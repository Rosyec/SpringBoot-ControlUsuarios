package com.example.miproyectowebspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/img/**", "/app/listar").permitAll()
                // .antMatchers("/app/ver/**").hasAnyRole("USER")
                // .antMatchers("/uploads/**").hasAnyRole("ADMIN")
                // .antMatchers("/app/form/**").hasAnyRole("ADMIN")
                // .antMatchers("/app/eliminar/**").hasAnyRole("ADMIN")
                // .antMatchers("/factura/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .successHandler(loginSuccessHandler)
                    .loginPage("/login")
                    // .defaultSuccessUrl("/app/listar")
                    .permitAll()
                    .and()
                    .logout().permitAll()
                    .and()
                    .exceptionHandling().accessDeniedPage("/error_403");
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() throws Exception {

        PasswordEncoder passwordEncoder = this.passwordEncoder;
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        manager.createUser(
                User
                        .withUsername("root")
                        .password(passwordEncoder
                                .encode("admin"))
                        .roles("ADMIN", "USER")
                        .build());

        manager.createUser(User
                .withUsername("ceysor")
                .password(passwordEncoder
                        .encode("12345"))
                .roles("USER")
                .build());

        return manager;
    }
}
