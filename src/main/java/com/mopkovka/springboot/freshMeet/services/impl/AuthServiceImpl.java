package com.mopkovka.springboot.freshMeet.services.impl;

import com.mopkovka.springboot.freshMeet.domain.Role;
import com.mopkovka.springboot.freshMeet.domain.UserEntity;
import com.mopkovka.springboot.freshMeet.dto.Auth.JwtAuthResponse;
import com.mopkovka.springboot.freshMeet.dto.Auth.SignInRequest;
import com.mopkovka.springboot.freshMeet.dto.Auth.SignUpRequest;
import com.mopkovka.springboot.freshMeet.services.AuthService;
import com.mopkovka.springboot.freshMeet.services.JwtService;
import com.mopkovka.springboot.freshMeet.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthResponse signUp(SignUpRequest request) {
        var user = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userService.createUser(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthResponse(jwt);
    }

    @Override
    public JwtAuthResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthResponse(jwt);
    }
}
