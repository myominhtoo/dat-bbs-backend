package com.penta.aiwmsbackend.model.bean;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.penta.aiwmsbackend.model.entity.User;

public class CustomUserDetails implements UserDetails{

    private User user;

    public CustomUserDetails( User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList( new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
       return this.user.getPassword();
    }

    @Override
    public String getUsername() {
       return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
       return true;
    }

    @Override
    public boolean isAccountNonLocked() {
       return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
       return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
