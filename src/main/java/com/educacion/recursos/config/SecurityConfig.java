package com.educacion.recursos.config; // Asegúrate de que coincida con tu carpeta

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filtro(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/css/**", "/images/**").permitAll() // Público
                .requestMatchers("/admin/**").hasRole("ADMIN") // Solo para admin
                .anyRequest().authenticated() // Todo lo demás pide login
            )
            .formLogin(form -> form
                .loginPage("/login") // Tu página personalizada de login
                .defaultSuccessUrl("/recursos", true) // A dónde va tras entrar
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        
        return http.build();
    }

    @Bean
    public UserDetailsService usuarios() {
        // Usuario para ti (como docente)
        UserDetails usuario = User.builder()
            .username("docente")
            .password(encoder().encode("1234"))
            .roles("USER")
            .build();

        // Usuario Administrador
        UserDetails admin = User.builder()
            .username("admin")
            .password(encoder().encode("admin123"))
            .roles("ADMIN", "USER")
            .build();

        return new InMemoryUserDetailsManager(usuario, admin);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}