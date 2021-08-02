package com.robin.eduauthorityboot.service;

import com.robin.eduauthorityboot.entity.UserDTO;

public interface UserService {

    public UserDTO login(String phone, String password);

    public UserDTO checkToken(String token);
}
