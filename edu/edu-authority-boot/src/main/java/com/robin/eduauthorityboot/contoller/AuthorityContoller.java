package com.robin.eduauthorityboot.contoller;

import com.robin.eduauthorityboot.entity.UserDTO;
import com.robin.eduauthorityboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@CrossOrigin
public class AuthorityContoller {

    @Autowired
    private UserService userService;

    @GetMapping("login")
    public UserDTO login(String phone, String password) {
        return userService.login(phone, password);
    }

    @GetMapping("checkToken")
    public UserDTO checkToken(String token) {
        System.out.println("待校验的token = " + token);
        return userService.checkToken(token);
    }
}