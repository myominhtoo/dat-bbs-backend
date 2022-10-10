package com.penta.aiwmsbackend.model.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.Random;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.exception.custom.DuplicateEmailException;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.UserRepo;
import com.penta.aiwmsbackend.model.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;
    private AuthenticationManager authenticationManager;
    private EmailServiceImpl emailServiceImpl;
    private CustomUserDetailsService customUserDetailsService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl( UserRepo userRepo ,
     EmailServiceImpl emailServiceImpl , 
     AuthenticationManager authenticationManager , 
     CustomUserDetailsService customUserDetailsService,
     BCryptPasswordEncoder passwordEncoder  ){
        this.userRepo = userRepo;
        this.emailServiceImpl=emailServiceImpl;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
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
            // String link = "http://localhost:4200/register?email="+email+"&code="+user.getCode()+"";
            emailServiceImpl.verifyEmail("sunandaraung1211@gmail.com", "DAT BBMS",email,"Verify Your Email For BBMS",
            "<h2>Your Verification Code is : "+user.getCode()+"</h2>");
            isSuccess = true;
        }catch( Exception e ){  
            System.out.println(e);
            isSuccess = false;
        }
        return isSuccess;
    }


    @Override
    public boolean loginUser( User user ) throws BadCredentialsException , UsernameNotFoundException {
        Authentication authentication;
        boolean loginStatus = false;
        UserDetails userDetails =  this.customUserDetailsService.loadUserByUsername(user.getEmail());
        if(this.passwordEncoder.matches( user.getPassword() , userDetails.getPassword() )){
            authentication = this.authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( user.getEmail(), userDetails.getPassword()));
            SecurityContextHolder.getContext().setAuthentication( authentication );
            loginStatus = true;
        }else{
            throw new BadCredentialsException("Invalid email or password1!");
        }
        return loginStatus;
    }
  
}
