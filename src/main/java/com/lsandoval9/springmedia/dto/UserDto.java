package com.lsandoval9.springmedia.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDto {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 5, max = 20, message = "choose an username between 5 and 20 characters")
    @Pattern(regexp = "[a-zA-Z0-9_*$.&^\\s]*")
    private final String username;

    @NotBlank(message = "email cannot be blank")
    @Email(message = "please choose a valid email address, E.G: something@email.com")
    private  String email;

    @NotBlank(message = "password cannot be blank")
    private  String password;

    @NotBlank(message = "firstname cannot be blank")
    private  String firstname;

    @NotBlank(message = "lastname cannot be blank")
    private  String lastname;


    public UserDto(String username,
                   String email,
                   String password,
                   String firstname,
                   String lastname) {


        this.username = username;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
