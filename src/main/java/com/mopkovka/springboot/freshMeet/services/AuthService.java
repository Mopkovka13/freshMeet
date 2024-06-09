package com.mopkovka.springboot.freshMeet.services;

import com.mopkovka.springboot.freshMeet.dto.Auth.JwtAuthResponse;
import com.mopkovka.springboot.freshMeet.dto.Auth.SignInRequest;
import com.mopkovka.springboot.freshMeet.dto.Auth.SignUpRequest;

public interface AuthService {
    public JwtAuthResponse signUp(SignUpRequest signUpRequest);
    public JwtAuthResponse signIn(SignInRequest signInRequest);
}