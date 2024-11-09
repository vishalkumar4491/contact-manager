package com.contact_manager.scm.forms;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {
    @NotBlank(message="Username is required")
    @Size(min=3, message="Min 3 character required")
    @Size(max=20, message="Maimum 20 characted only needed")
    private String name;
    @Email(message="Invalid Email Address")
    @NotBlank
    private String email;
    @NotBlank(message="Password is required")
    @Size(min=4, max=15)
    private String password;
    @NotBlank(message="About is required")
    private String about;
    @Size(min=8, max=12, message="Phone Number must be between 8 to 12 Numbers")
    // @Pattern(regexp="^\\+[1-9]\\d{1,14}$")
    private String phoneNumber;
}
