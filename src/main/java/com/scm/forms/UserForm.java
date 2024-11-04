package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class UserForm {

    @NotBlank(message = "Name is required")
    @Size(message = "Name should be between 3 and 50 characters", min = 3, max = 50)
    private String name;
    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;
    @Size(min=6, message = "Password is min 6 characters")
    private String password;
    @NotBlank(message = "About is required")
    private String about;
    @Size(min = 8, max = 12, message = "Invalid Phone Number")
    private String phoneNumber;
}