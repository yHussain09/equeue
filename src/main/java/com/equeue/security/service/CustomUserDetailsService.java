package com.equeue.security.service;

import com.equeue.security.entity.User;
import com.equeue.security.jwt.UserPrincipal;
import com.equeue.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getOrganizer().getId(),
                user.getRoles().stream()
                        .map(r -> new SimpleGrantedAuthority(
                                "ROLE_" + r.getName().name()))
                        .collect(Collectors.toSet())
        );
    }

    public UserPrincipal loadById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return loadUserByUsername(user.getUsername());
    }
}

