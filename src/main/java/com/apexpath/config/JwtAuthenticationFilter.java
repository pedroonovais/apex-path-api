package com.apexpath.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String tokenPrefix = "Bearer ";

        if (authHeader == null || !authHeader.startsWith(tokenPrefix)) {
            log.debug("Requisição sem cabeçalho JWT válido. URI: {}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtToken = authHeader.substring(tokenPrefix.length()).trim();

        if (jwtToken.isEmpty()) {
            log.warn("Token JWT vazio no cabeçalho Authorization.");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String username = jwtService.extractUsername(jwtToken);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtService.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    log.debug("Usuário autenticado com JWT: {}", username);
                } else {
                    log.warn("Falha na validação do JWT para usuário: {}", username);
                }
            }
        } catch (Exception ex) {
            log.error("Erro ao validar ou autenticar token JWT. Causa: {}", ex.getMessage(), ex);
            // Mantém fluxo normal mesmo em caso de erro de autenticação
        }

        filterChain.doFilter(request, response);
    }
}
