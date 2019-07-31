package com.cc.service.impl;

import com.cc.pojo.User;
import com.cc.service.IUserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class userServiceImpl implements IUserService {

    private User user;
    @Override
    public void getUserService() {
        System.out.println(user.toString());
    }
}
