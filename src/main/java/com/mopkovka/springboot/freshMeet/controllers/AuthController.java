package com.mopkovka.springboot.freshMeet.controllers;

import com.mopkovka.springboot.freshMeet.dto.Auth.JwtAuthResponse;
import com.mopkovka.springboot.freshMeet.dto.Auth.SignInRequest;
import com.mopkovka.springboot.freshMeet.dto.Auth.SignUpRequest;
import com.mopkovka.springboot.freshMeet.services.AuthService;
import com.mopkovka.springboot.freshMeet.services.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthController {
    private final AuthService authService;
    private final UserService userService; // Для тестов

    @Operation(
            description = "Get JWT Token for new register user",
            summary = "Register new user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "JWT Token"
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            description = "Null body"
                    )
            }
    )
    @PostMapping("/sign-up")
    public JwtAuthResponse signUp(@RequestBody @Valid SignUpRequest request){
        return authService.signUp(request);
    }

    @Operation(summary = "Login user")
    @PostMapping("/sign-in")
    public JwtAuthResponse signIn(@RequestBody @Valid SignInRequest request){
        return authService.signIn(request);
    }

    @GetMapping("/user")
    @Operation(summary = "Доступен только авторизованным пользователям")
    public String exampleUser() {
        return "Hello, user!";
    }

    @GetMapping("/admin")
    @Operation(summary = "Доступен только пользователям с ролью ADMIN")
    public String exampleAdmin() {
        return "Hello, admin!";
    }

    @GetMapping("/get-admin")
    @Operation(summary = "Получить админ для текущего пользователя")
    public void getAdmin() {
        userService.getAdmin();
    }

    @GetMapping("/hide-method")
    @Hidden
    @Operation(summary = "Скрытый метод")
    public void hideMethod() {}
}
