package com.penta.aiwmsbackend.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.UserRepo;
import com.penta.aiwmsbackend.model.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    @Autowired
    public UserServiceImpl( UserRepo userRepo ){
        this.userRepo = userRepo;
    }

    @Override
    public boolean createUser(User user) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isDuplicateEmail(String email) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
