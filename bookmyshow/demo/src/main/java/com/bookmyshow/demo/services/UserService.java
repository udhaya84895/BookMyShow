package com.bookmyshow.demo.services;

import com.bookmyshow.demo.dtos.SignupUserRequestDto;
import com.bookmyshow.demo.models.User;
import com.bookmyshow.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User signUp(SignupUserRequestDto request){

        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if(user.isPresent()) throw new RuntimeException();

        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        String passWord = request.getPassword();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        newUser.setPassword(passwordEncoder.encode(passWord));

        return userRepository.save(newUser);
    }

    public boolean login(String email, String password){
        Optional<User> user = userRepository.findByEmail(email);
        if(!user.isPresent()) throw new RuntimeException();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return new BCryptPasswordEncoder().matches(password, user.get().getPassword());
    }
}
