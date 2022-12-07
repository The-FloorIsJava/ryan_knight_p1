package com.revature.EmployeeTicketApplication.Token;

import com.revature.EmployeeTicketApplication.AccountExceptions.InvalidTokenException;
import com.revature.EmployeeTicketApplication.Models.PasswordProtectedProfile;
import com.revature.EmployeeTicketApplication.Models.ProfileFactory;
import io.javalin.http.Context;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

import javax.crypto.spec.SecretKeySpec;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;

public class JWTUtility {

    private final Properties properties;
    private final byte[] bytes;


    public JWTUtility() throws IOException {

        // Initialize and configure properties
        properties = new Properties();
        properties.load(new FileReader("src/main/resources/config.properties"));

        // Initialize and configure bytes.
        bytes = Base64.getEncoder().encode(properties.getProperty("jwt-secret").getBytes());
    }

    public String createToken(PasswordProtectedProfile profile) {
        JwtBuilder tokenBuilder = Jwts.builder()
                .setId(profile.getUsername())
                .setSubject(profile.getFirstName() + " " + profile.getLastName())
                .setIssuer("employee-reimbursement application")
                .claim("administrator",profile.isAdministrator())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 60*60*1000))
                .signWith(new SecretKeySpec(bytes,"HmacSHA256"));

        return tokenBuilder.compact();
    }

    private Optional<PasswordProtectedProfile> parseToken(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(bytes)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Optional.of(ProfileFactory.getPasswordProtectedProfile(
                claims.getId(),
                claims.getSubject().split(" ")[0],
                claims.getSubject().split(" ")[1],
                null,
                Boolean.parseBoolean(claims.get("administrator").toString())
        ));
    }

    public boolean isTokenValid(String token){
        if(token == null || token.trim().equals("")) {
            return false;
        } else {
            return parseToken(token).isPresent();
        }
    }

    public PasswordProtectedProfile extractToken(String token) {

        if (!isTokenValid(token)) {
            throw new InvalidTokenException();
        }
        return parseToken(token).orElseThrow(InvalidTokenException::new);
    }


}
