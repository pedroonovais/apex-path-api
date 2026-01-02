package com.apexpath.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apexpath.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.apexpath.model.User userEntity = userRepository.findByEmail(email)
            .orElseThrow(() -> {
                log.warn("Usuário não encontrado com email: {}", email);
                return new UsernameNotFoundException("Usuário não encontrado: " + email);
            });

        log.debug("Usuário encontrado: {} (ID: {})", userEntity.getEmail(), userEntity.getId());

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        
        return User.builder()
            .username(userEntity.getEmail())
            .password(userEntity.getPassword())
            .authorities(Collections.singletonList(authority))
            .build();
    }
}

