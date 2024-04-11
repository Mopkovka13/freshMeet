package com.mopkovka.springboot.freshMeet.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/register")
    public String register(){
        return "register";
    }
}
