package com.penta.aiwmsbackend.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import static com.penta.aiwmsbackend.model.constant.JwtConstant.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service 
// @Component
public class JwtProvider {

    // @Value("${jwt.secret}")
    private String SECRET ="qHcBipWQACWwXRNUjVgQSqqbBVfyfvIC";
    

    public String generateToken(String email , String password ) {
        Algorithm algorithm = Algorithm.HMAC512(SECRET);

        Map<String,String> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("password", password);

        String [] claims = { email , password };

        String token = JWT.create()
                .withIssuer(ISSUER)
                .withArrayClaim("data", claims )
                .withExpiresAt( new Date( System.currentTimeMillis() + 604800000 ) )
                .sign(algorithm);
        return token;
    }

    public static void main(String[] args) {
        JwtProvider jwtProvider = new JwtProvider();
        System.out.println();
    }

    private JWTVerifier getVerifier() {
        JWTVerifier verifier = null;
        Algorithm algorithm = Algorithm.HMAC512(this.SECRET);
        try {
            verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Invalid token!");
        }
        return verifier;
    }

    public boolean isTokenValid( String token ){
        JWTVerifier verifier = getVerifier();
        String [] payload = verifier.verify( token ).getClaim("data").asArray(String.class);
        return payload != null && !this.isExpired( verifier , token );
    }

    private boolean isExpired( JWTVerifier verifier , String token ){
        Date expiration = verifier.verify( token ).getExpiresAt();
        return expiration.before(new Date());
    }

    public String[] getPayload( String token ){
       JWTVerifier verifier = this.getVerifier();
       return verifier.verify(token).getClaim("data").asArray(String.class);
    }

    // , HttpServletRequest req 
    public Authentication getAuthentication( String email , String password  ){
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken( email , password );
        // authentication.setDetails( new WebAuthenticationDetailsSource().buildDetails(req));
        return authentication;
    }

}
