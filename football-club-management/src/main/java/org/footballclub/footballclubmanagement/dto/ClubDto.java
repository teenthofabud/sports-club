package org.footballclub.footballclubmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClubDto {
    @NotBlank(message= "Name is required")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message="Invalid email format")
    private String email;
    @NotBlank(message = "abbreviation is required")
    private String abbreviation;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    @NotBlank(message = "logo url is required")
    private String logoUrl;
    @NotBlank(message = "Club website is required")
    private String website;
}
