package com.penta.aiwmsbackend.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import static com.penta.aiwmsbackend.model.constant.JwtConstant.*;

@Component
public class JwtProvider {
    
    @Value("${jwt.secret}")
    private String SECRET;

    public String generateToken( String email ){       
        Algorithm algorithm = Algorithm.HMAC512( SECRET );       
        String token = JWT.create()
                       .withIssuer( ISSUER )
                       .withSubject( email )
                       .sign( algorithm );
        return token;
    }

    private JWTVerifier getVerifier(){
        JWTVerifier verifier = null;
        Algorithm algorithm = Algorithm.HMAC512( this.SECRET );
        try{
            verifier = JWT.require( algorithm ).withIssuer( ISSUER ).build();
        }catch( JWTVerificationException e ){
            throw new JWTVerificationException("Invalid token!");
        }
        return verifier;
    }

}
