package com.gestion.filmotheque.service;

import com.gestion.filmotheque.dto.UserRegistrationDto;
import com.gestion.filmotheque.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByEmail(String email);

    User save(UserRegistrationDto registrationDto);
}