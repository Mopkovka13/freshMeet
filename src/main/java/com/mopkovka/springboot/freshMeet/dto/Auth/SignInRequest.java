package com.mopkovka.springboot.freshMeet.dto.Auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request for sign in")
public class SignInRequest {
    @Schema(description = "Username", example = "Stepan")
    @Size(min = 5, max = 50, message = "Username must be between 5 and 50 characters")
    @NotBlank(message = "Username can't be empty")
    private String username;

    @Schema(description = "Password", example = "M7Secr3tP@ss")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    @NotBlank(message = "Password can't be empty")
    private String password;
}
