package com.apexpath.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfig {
    
    /**
     * Bean para codificação de senhas usando BCrypt
     * @return PasswordEncoder configurado com BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("Configurando BCryptPasswordEncoder para codificação de senhas");
        return new BCryptPasswordEncoder();
    }
    
    /**
     * Configuração da cadeia de filtros de segurança
     * Permite criação de usuário sem autenticação (POST /users)
     * @param http Configuração HTTP do Spring Security
     * @param jwtAuthenticationFilter Filtro JWT injetado diretamente no método para evitar dependência circular
     * @return SecurityFilterChain configurado
     * @throws Exception em caso de erro na configuração
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        log.info("Configurando SecurityFilterChain com JWT");
        
        http
            // Desabilita CSRF para APIs stateless (JWT)
            .csrf(csrf -> csrf.disable())
            
            // Configura política de sessão como stateless (não cria sessões HTTP)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // Configura regras de autorização
            .authorizeHttpRequests(auth -> auth
                // Permite criação de usuário sem autenticação
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                // Todos os outros endpoints requerem autenticação
                .anyRequest().authenticated()
            )
            
            // Adiciona o filtro JWT antes do filtro de autenticação padrão
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}