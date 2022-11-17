package com.penta.aiwmsbackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.penta.aiwmsbackend.model.constant.SecurityConstant.*;

import com.penta.aiwmsbackend.filter.AuthenticationEntryPointHandler;
import com.penta.aiwmsbackend.filter.AuthorizationFilter;
import com.penta.aiwmsbackend.filter.CustomAccessDeniedHandler;
import com.penta.aiwmsbackend.model.service.UserService;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder;
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    private AuthenticationEntryPointHandler authenticationEntryPointHandler;
    private AuthorizationFilter authorizationFilter;
    
    @Autowired
    public SecurityConfig( @Lazy UserService userService , BCryptPasswordEncoder passwordEncoder ,
         CustomAccessDeniedHandler customAccessDeniedHandler ,
         AuthenticationEntryPointHandler authenticationEntryPointHandler , 
         AuthorizationFilter authorizationFilter  ){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.authenticationEntryPointHandler = authenticationEntryPointHandler;
        this.authorizationFilter = authorizationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers( PUBLIC_URLS )
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPointHandler)
                .accessDeniedHandler(customAccessDeniedHandler)
                .and()
                .addFilterBefore( this.authorizationFilter , UsernamePasswordAuthenticationFilter.class)
                .formLogin().disable();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
        .passwordEncoder(passwordEncoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
