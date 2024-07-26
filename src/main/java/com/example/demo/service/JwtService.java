package com.example.demo.service;

import com.example.demo.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final String SECRET_KEY = "3c36865c2439d9d4b896548a026962b11089deb4caa0de234213f5b3a813eca2"; // Use environment variable for security

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Transactional
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Transactional
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject); // Assuming the subject is the email
    }

    @Transactional
    public boolean isValid(String token, UserDetails user) {
        String username = extractEmail(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    @Transactional
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    @Transactional
    public String generateToken(User user) {
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("roles", roles) // Add roles to the JWT claim
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3*24*60*60*1000)) // Corrected duration
                .signWith(getSigninKey())
                .compact();
    }

    @Transactional
    public List<String> extractRoles(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        // Extract roles from JWT. Assume roles are stored in a list
        return claims.get("roles", List.class);
    }



    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); // Fetch from environment variable
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
