package com.penta.aiwmsbackend.model.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.StackWalker.Option;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.Multipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.penta.aiwmsbackend.exception.custom.DuplicateEmailException;
import com.penta.aiwmsbackend.exception.custom.FileNotSupportException;
import com.penta.aiwmsbackend.exception.custom.InvalidCodeException;
import com.penta.aiwmsbackend.exception.custom.InvalidEmailException;
import com.penta.aiwmsbackend.model.entity.User;
import com.penta.aiwmsbackend.model.repo.UserRepo;
import com.penta.aiwmsbackend.util.MailTemplate;

@Service("userService")
public class UserService {

    // @Value("${project.image}")
    private String PATH = System.getProperty("java.class.path").split(";")[0].replace("target\\classes", "")
            + "src\\main\\resources\\static\\img\\";

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

    public User updateUser(User user) throws InvalidEmailException, InvalidCodeException {
        Optional<User> optionalUser = this.userRepo.findById(user.getId());
        if (optionalUser.isEmpty()) {
            throw new InvalidEmailException("Invalid email!");
        }

        User savedUser = optionalUser.get();
        if (this.passwordEncoder.matches(user.getConfirmpassword(), savedUser.getPassword())) {
            if (user.getPassword() == null || user.getPassword() == "") {
                savedUser.setPassword(savedUser.getPassword());
            } else {
                savedUser.setPassword(this.passwordEncoder.encode(user.getPassword()));
            }

            savedUser.setUsername(user.getUsername());
            savedUser.setBio(user.getBio());
            savedUser.setGender(user.getGender());
            savedUser.setPhone(user.getPhone());
            savedUser.setPosition(user.getPosition());

            return userRepo.save(savedUser);
        }
        return null;
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

            emailService.sendToOneUser("datofficial22@gmail.com", "DAT", email, "Verify Your Email For Login",
                                       MailTemplate.getTemplate("Verify Your Email!", "Click Here To Register!",
                                       "http://localhost:4200/register?email="+email +"&code="+ user.getCode()));
            isSuccess = true;
        } catch (Exception e) {
            System.out.println(e);
            isSuccess = false;
        }
        return isSuccess;
    }

    public User loginUser(User user) throws BadCredentialsException, UsernameNotFoundException {
        Authentication authentication;
        User savedUser = this.userRepo.findByEmail(user.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Not found!"));
        if (this.passwordEncoder.matches(user.getPassword(), savedUser.getPassword())) {
            // authentication = this.authenticationManager.authenticate( new
            // UsernamePasswordAuthenticationToken( userDetails.getPassword(),
            // userDetails.getPassword()));
            // SecurityContextHolder.getContext().setAuthentication( authentication );
        } else {
            throw new BadCredentialsException("Invalid email or password!");
        }
        return savedUser;
    }

    public User findById(Integer userId) {
        Optional<User> user = this.userRepo.findById(userId);
        if (user.isPresent()) {
            User userObj = user.get();
            userObj.setPassword("");
            return userObj;
        }
        return null;
    }

    public User updateImage(MultipartFile path, Integer id) throws IOException, FileNotSupportException {
        String fullPath = PATH + path.getOriginalFilename();
        String fileName = StringUtils.cleanPath(path.getOriginalFilename());
        String extension = path.getContentType();

        Optional<User> user = this.userRepo.findById(id);

        if (extension.equals("image/jpg") || extension.equals("image/png") || extension.equals("image/jpeg")) {
            if (user.get().isValidUser()) {
                user.get().setImageUrl(fileName);

                path.transferTo(new File(fullPath));
            } else {
                return null;
            }
        } else {
            throw new FileNotSupportException("File not supported");
        }

        User savedUser = user.get();
        savedUser.setImageUrl(fileName);
        return this.userRepo.save(savedUser);
    }

    public User deleteImage(User user) throws InvalidEmailException {
        Optional<User> optionalUser = this.userRepo.findById(user.getId());
        if (optionalUser.isEmpty()) {
            throw new InvalidEmailException("Invalid email!");
        }

        User savedUser = optionalUser.get();
        savedUser.setImageUrl(null);

        return userRepo.save(savedUser);

    }

    public boolean forgetPassword(String email) throws InvalidEmailException {

        boolean isSuccess = false;

        Optional<User> optionalUser = this.userRepo.findByEmail(email);
        if (optionalUser.isEmpty()) {

            throw new InvalidEmailException("Invalid email!");

        } else {

            User forgetUser = optionalUser.get();

            Random ram = new Random();

            forgetUser.setCode(ram.nextInt(1000000));
            // forgetUser.setEmail(user.getEmail());

            userRepo.save(forgetUser);

            Optional<User> reUser = this.userRepo.findByEmail(email);
            User resetUser = reUser.get();

            try {

                emailService.sendToOneUser("sunandaraung1211@gmail.com", "DAT BBMS", email,
                        "For Forget password",
                        "<h2>Your reset Code is : " + resetUser.getCode() + "</h2>");
                isSuccess = true;
            } catch (Exception e) {
                System.out.println(e);
                isSuccess = false;
            }
            return isSuccess;

        }

    }

    public Boolean changePassword(User user) throws InvalidEmailException {

        Optional<User> optionalUser = this.userRepo.findByEmail(user.getEmail());
        User isUser = optionalUser.get();
        boolean isSuccess = false;

        if (optionalUser.isEmpty()) {
            throw new InvalidEmailException("Invalid email!");
        } else {

            if (user.getCode().equals(isUser.getCode())) {

                isUser.setPassword(this.passwordEncoder.encode(user.getPassword()));
                ;

                userRepo.save(isUser);
                isSuccess = true;
            }

        }
        return isSuccess;
    }

    public List<User> getRpMember(Integer id) {
        return userRepo.findReportMember(id);
    }

}
 