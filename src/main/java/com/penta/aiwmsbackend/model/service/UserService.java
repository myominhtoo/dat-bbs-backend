package com.penta.aiwmsbackend.model.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

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
import com.penta.aiwmsbackend.exception.custom.InvalidCodeException;
import com.penta.aiwmsbackend.exception.custom.InvalidEmailException;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.UserRepo;

@Service("userService")
public class UserService {
    private UserRepo userRepo;
    private AuthenticationManager authenticationManager;
    private EmailService emailService;
    private CustomUserDetailsService customUserDetailsService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo,
            EmailService emailService,
            AuthenticationManager authenticationManager,
            CustomUserDetailsService customUserDetailsService,
            BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean createUser(User user) throws InvalidEmailException, InvalidCodeException {
        boolean createStatus = false;
        Optional<User> optionalUser = this.userRepo.findByEmail(user.getEmail());
        if (optionalUser.isEmpty()) {
            throw new InvalidEmailException("Invalid email!");
        }

        User savedUser = optionalUser.get();
        if (!savedUser.getCode().equals(user.getCode())) {
            throw new InvalidCodeException("Invalid verfication code!");
        }

        savedUser.setUsername(user.getUsername());
        savedUser.setPassword(this.passwordEncoder.encode(user.getPassword()));
        savedUser.setValidUser(true);

        if (this.userRepo.save(savedUser) != null) {
            createStatus = true;
        }
        return createStatus;
    }

    public List<User> getUsers() {
        return this.userRepo.findAll().stream()
                .map(user -> {
                    user.setPassword("");
                    return user;
                })
                .collect(Collectors.toList());
    }

    public boolean isDuplicateEmail(String email) {
        boolean isDuplicateEmail = false;
        Optional<User> saveUser = userRepo.findByEmail(email);
        if (saveUser.isPresent()) {
            isDuplicateEmail = true;
        }
        return isDuplicateEmail;
    }

    public boolean sendVertification(String email)
            throws DuplicateEmailException, UnsupportedEncodingException, MessagingException {
        boolean isSuccess = false;
        boolean shouldStore = true;
        boolean isDuplicateEmail = isDuplicateEmail(email);
        if (isDuplicateEmail) {
            if (userRepo.findByEmail(email).get().isValidUser()) {
                throw new DuplicateEmailException("Duplicate email!");
            } else {
                shouldStore = false;
            }
        }
        User user = new User();

        if (shouldStore) {
            // setting new email
            user.setEmail(email);
        } else {
            // to get user with this email
            user = this.userRepo.findByEmail(email).get();
        }

        Random random = new Random();
        user.setCode(random.nextInt(100000000));
        user.setValidUser(false);
        user.setDeleteStatus(false);

        try {
            this.userRepo.save(user);
            // String link =
            // "http://localhost:4200/register?email="+email+"&code="+user.getCode()+"";
            emailService.sendToOneUser("sunandaraung1211@gmail.com", "DAT BBMS", email, "Verify Your Email For BBMS",
                    "<h2>Your Verification Code is : " + user.getCode() + "</h2>");
            isSuccess = true;
        } catch (Exception e) {
            System.out.println(e);
            isSuccess = false;
        }
        return isSuccess;
    }

    public User loginUser(User user) throws BadCredentialsException, UsernameNotFoundException {
        Authentication authentication;
        User savedUser = this.userRepo.findByEmail( user.getEmail() )
                         .orElseThrow(() -> new UsernameNotFoundException("Not found!"));
        if (this.passwordEncoder.matches(user.getPassword(),savedUser.getPassword())) {
            // authentication = this.authenticationManager.authenticate( new
            // UsernamePasswordAuthenticationToken( userDetails.getPassword(),
            // userDetails.getPassword()));
            // SecurityContextHolder.getContext().setAuthentication( authentication );
        } else {
            throw new BadCredentialsException("Invalid email or password1!");
        }
        return savedUser;
    }
}
