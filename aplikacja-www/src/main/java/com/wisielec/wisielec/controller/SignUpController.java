package com.wisielec.wisielec.controller;

import com.wisielec.wisielec.domain.User;
import com.wisielec.wisielec.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Emerex on 08/12/2017.
 */

@Controller
public class SignUpController {
    //Test of firebase


    @Autowired
    UserRepository userRepository;

    @RequestMapping("/register")
    public void register(){
        User user = new User();
        user.setUserName("Wojtek");
        userRepository.addNewUser(user);
    }

}
