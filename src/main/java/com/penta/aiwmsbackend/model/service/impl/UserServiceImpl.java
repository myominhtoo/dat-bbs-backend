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
            if (saveUser.get().isValidUser()){
                isDuplicateEmail = true;
            } ;
        }
      return isDuplicateEmail;
    }

    @Override
    public void sendVertification(String email) throws DuplicateEmailException, UnsupportedEncodingException, MessagingException {
        if ( isDuplicateEmail(email)){
            throw new DuplicateEmailException( "Duplicated Email");
        }else {
            User user = new User();
            user.setEmail(email);
            Random random = new Random();
            user.setCode(random.nextInt(100000000));
            user.setValidUser(false);
            user.setDeleteStatus(false);

            this.userRepo.save(user);
            emailServiceImpl.verifyEmail("sunandaraung1211@gmail.com", "You SEE",email,"hello","we love u thein oo");
        }
        
      
    }

    
    
}
