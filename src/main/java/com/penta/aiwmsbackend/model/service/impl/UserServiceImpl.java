package com.penta.aiwmsbackend.model.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.Random;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.exception.custom.DuplicateEmailException;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.UserRepo;
import com.penta.aiwmsbackend.model.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    private EmailServiceImpl emailServiceImpl;

    @Autowired
    public UserServiceImpl( UserRepo userRepo , EmailServiceImpl emailServiceImpl ){
        this.userRepo = userRepo;
        this.emailServiceImpl=emailServiceImpl;
    }

    
    @Override
    public boolean createUser(User user) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isDuplicateEmail(String email)  {
        boolean isDuplicateEmail = false ;
        Optional<User> saveUser = userRepo.findByEmail(email);
        if (saveUser.isPresent()){
            isDuplicateEmail = true;
        }
      return isDuplicateEmail;
    }

    @Override
    public boolean sendVertification(String email) throws DuplicateEmailException, UnsupportedEncodingException, MessagingException {
        boolean isSuccess = false;
        boolean shouldStore = true;
        boolean isDuplicateEmail = isDuplicateEmail(email);
        if ( isDuplicateEmail ){
            if( userRepo.findByEmail(email).get().isValidUser()){
                throw new DuplicateEmailException("Duplicate email!");
            }else{
                shouldStore = false;
            }
        }
        User user = new User();

        if( shouldStore ){
            // setting new email
            user.setEmail(email);
        }else{
            // to get user with this email 
            user = this.userRepo.findByEmail(email).get();
        }

        Random random = new Random();
        user.setCode(random.nextInt(100000000));
        user.setValidUser(false);
        user.setDeleteStatus(false);


        try{
            this.userRepo.save(user);
            emailServiceImpl.verifyEmail("sunandaraung1211@gmail.com", "You SEE",email,"hello","we love u thein oo");
            isSuccess = true;
        }catch( Exception e ){
            isSuccess = false;
        }
        return isSuccess;
    }

    
    
}
