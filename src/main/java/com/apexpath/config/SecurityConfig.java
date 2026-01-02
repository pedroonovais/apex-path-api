package com.apexpath.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final UserDetailsService userDetailsService;
    
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
     * Bean para AuthenticationManager usado no login
     * O Spring Security cria automaticamente quando detecta UserDetailsService e PasswordEncoder
     * @param config Configuração de autenticação do Spring Security
     * @return AuthenticationManager configurado
     * @throws Exception em caso de erro na configuração
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    /**
     * Configuração da cadeia de filtros de segurança
     * Permite criação de usuário e login sem autenticação
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
                // Permite criação de usuário e login sem autenticação
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                // Todos os outros endpoints requerem autenticação
                .anyRequest().authenticated()
            )
            
            // Configura UserDetailsService e PasswordEncoder para autenticação
            .userDetailsService(userDetailsService)
            
            // Adiciona o filtro JWT antes do filtro de autenticação padrão
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}