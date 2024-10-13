package com.rentit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {
    @NotNull @NotBlank
    @Email(message = "Email should be valid")
    private String email;
    @NotNull @NotBlank
    @Size(min = 6, max = 20, message = "Password should be 6-20 characters long")
    private String password;
}
