package com.mopkovka.springboot.freshMeet.dto.Auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request for sign up")
public class SignUpRequest {
    @Schema(description = "Username", example = "Stepan")
    @Size(min = 5, max = 50, message = "Username must be between 5 and 50 characters")
    @NotBlank(message = "Username can't be empty")
    private String username;

    @Schema(description = "Email", example = "stepan@gmail.com")
    @Size(min = 5, max = 255, message = "Email must be between 5 and 255 characters")
    @NotBlank(message = "Email can't be empty")
    @Email(message = "Email must be in format email@domain.com")
    private String email;

    @Schema(description = "Password", example = "M7Secr3tP@ss")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    @NotBlank(message = "Password can't be empty")
    private String password;
}
