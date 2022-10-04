package com.penta.aiwmsbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping( value = "/" )
    public String hello(){
        System.out.println(passwordEncoder.matches("hell","$2a$10$zz1uhcWbZdwcgVhKthPESeytabp1w5kPlYEPFBeIJ/VSqEnW/DZhO"));
        return "Hello world";
    }

}
