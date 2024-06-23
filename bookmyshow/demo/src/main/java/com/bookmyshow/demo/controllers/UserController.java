package com.bookmyshow.demo.controllers;

import com.bookmyshow.demo.dtos.ResponseStatus;
import com.bookmyshow.demo.dtos.SignUpUserResponseDto;
import com.bookmyshow.demo.dtos.SignupUserRequestDto;
import com.bookmyshow.demo.models.User;
import com.bookmyshow.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    private UserService userService;
    public SignUpUserResponseDto signUp(SignupUserRequestDto request) {
        User user = userService.signUp(request);
        return new SignUpUserResponseDto(user.getId(), ResponseStatus.SUCCESS);
    }

    public boolean login(String email, String password){
        return userService.login(email, password);
    }
}
