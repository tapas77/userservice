package com.auth1.auth.learning.controllers;

import com.auth1.auth.learning.dtos.LoginRequestDto;
import com.auth1.auth.learning.dtos.SignupRequestDto;
import com.auth1.auth.learning.models.Token;
import com.auth1.auth.learning.models.User;
import com.auth1.auth.learning.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public User signUp(@RequestBody SignupRequestDto signupRequestDto){
        return userService.signUp(signupRequestDto.getEmail(),
                signupRequestDto.getPassword(),signupRequestDto.getName());
    }
    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto loginRequestDto){
        return userService.login(loginRequestDto.getEmail(),
                loginRequestDto.getPassword());
    }
    @PostMapping("/logout/{token}")
    public ResponseEntity<Void> logout(@PathVariable("token") String token){
        userService.logout(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("validateToken/{token}")
    public boolean validate(@PathVariable("token") String token){
        return userService.validateToken(token);
    }
}
