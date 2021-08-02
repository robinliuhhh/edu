package com.robin.eduuserboot.service.impl;

import com.robin.eduauthorityboot.entity.User;
import com.robin.eduuserboot.mapper.UserMapper;
import com.robin.eduuserboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void updateUser(Integer userid, String newName, String imgfileId) {
        User user = new User();
        user.setId(userid);
        user.setName(newName);
        user.setPortrait(imgfileId);
        userMapper.updateById(user);
    }

    @Override
    public void updatePassword(Integer userid, String newPwd) {
        User user = new User();
        user.setId(userid);
        user.setPassword(newPwd);
        userMapper.updateById(user);
    }
}
