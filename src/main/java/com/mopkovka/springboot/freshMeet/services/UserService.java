package com.mopkovka.springboot.freshMeet.services;

import com.mopkovka.springboot.freshMeet.domain.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    public UserEntity createUser(UserEntity user);
    public UserEntity getByUsername(String username);
    public UserDetailsService userDetailsService();
    public void getAdmin();
}
