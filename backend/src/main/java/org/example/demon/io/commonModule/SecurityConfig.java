package org.example.demon.io.commonModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Desabilita para facilitar os testes via Postman
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/usuarios/cadastrar", "/usuarios/login").permitAll() // Público
                        .anyRequest().authenticated() // Todo o resto exige login
                )
                .httpBasic(Customizer.withDefaults()); // Permite autenticação básica para testes
        return http.build();
    }
}