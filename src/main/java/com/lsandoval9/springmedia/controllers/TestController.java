package com.lsandoval9.springmedia.controllers;

import com.lsandoval9.springmedia.dto.UserDto;
import com.lsandoval9.springmedia.entities.UserEntity;
import com.lsandoval9.springmedia.security.auth.Roles;
import com.lsandoval9.springmedia.security.auth.SecurityUserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TestController {

    private final SecurityUserService securityUserService;

    public TestController(SecurityUserService securityUserService) {
        this.securityUserService = securityUserService;
    }

    @GetMapping(path = "/test")
    public String test() {

        return "ROLES: " + Roles.ADMIN.getRoleName() + " and ---> " + Roles.USER.getRoleName();

    }

    @PostMapping(path = "/create")
    public String create(@RequestBody @Valid UserDto userDto) {

        securityUserService.createUser(userDto);


        return "SUCCESS";
    }

    @GetMapping(path = "/{username}", produces = "application/json")
    public UserEntity getUsername(@PathVariable String username) {

        return securityUserService.getUser(username);

    }

}
