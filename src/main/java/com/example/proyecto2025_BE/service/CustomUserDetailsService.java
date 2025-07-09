package com.example.proyecto2025_BE.service;

import com.example.proyecto2025_BE.dao.UserDao;
import com.example.proyecto2025_BE.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Collections;
@Service
public class CustomUserDetailsService  implements UserDetailsService {
    @Autowired
    private UserDao userRepository;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userName));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }
}