package com.penta.aiwmsbackend.model.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.penta.aiwmsbackend.model.bean.CustomUserDetails;
import com.penta.aiwmsbackend.model.repo.UserRepo;

@Service
@Qualifier("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepo userRepo;

    public CustomUserDetailsService( UserRepo userRepo ){
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepo.findByEmail( email )
               .map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("Invalid User!"));
    }
    
}
