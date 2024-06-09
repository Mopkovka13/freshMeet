package com.mopkovka.springboot.freshMeet.services.impl;

import com.mopkovka.springboot.freshMeet.domain.Role;
import com.mopkovka.springboot.freshMeet.domain.UserEntity;
import com.mopkovka.springboot.freshMeet.repositories.UserRepository;
import com.mopkovka.springboot.freshMeet.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Primary // Обозначает какая реализация интерфейса будет использоваться
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserEntity createUser(
            UserEntity user
    ) {
        if (userRepository.existsByUsername(user.getUsername())) {
            // Заменить на свои исключения
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        return save(user);
    }

    @Override
    public UserEntity getByUsername(
            String username
    ) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    @Deprecated
    public void getAdmin() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = getByUsername(username);
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }

    private UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }
}
