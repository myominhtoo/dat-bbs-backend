package com.penta.aiwmsbackend.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.penta.aiwmsbackend.util.JwtProvider;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    private JwtProvider jwtProvider;

    // @Autowired
    public AuthorizationFilter( JwtProvider jwtProvider ){
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req , HttpServletResponse res, FilterChain filter )
            throws ServletException, IOException {
        if(req.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())){
            res.setStatus(HttpStatus.OK.value());
        }else{
            String authorizationHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
            if( authorizationHeader == null ){
                filter.doFilter( req , res );
                return;
            }else{
                String token = authorizationHeader;
                String [] payload = this.jwtProvider.getPayload(token);
                String email = payload[0];
                String password = payload[1];

                if( this.jwtProvider.isTokenValid(token) ){
                    System.out.println("valid");
                    SecurityContextHolder.getContext().setAuthentication(
                        this.jwtProvider.getAuthentication( email , password )
                    );
                    filter.doFilter( req , res );
                    return;
                }else{
                    SecurityContextHolder.clearContext();
                    filter.doFilter( req , res );
                }
            }
        }
    }

}
