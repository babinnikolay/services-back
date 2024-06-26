package ru.hukola.services.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author Babin Nikolay
 */
@Getter
@Setter
@RequiredArgsConstructor
public class UserRegistrationDto {
    @NotBlank
    @Length(min = 4)
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Min(6)
    private String password;
}
