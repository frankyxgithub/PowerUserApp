package com.example.PowerUser.Service;
import com.example.PowerUser.Repository.PowerUserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private final String SECRET_KEY = "5367566B59703373357638792F423F4528482B4D6251655468576D5A71347437";

    @Autowired
    private PowerUserRepository userRepository;

    private Key getSigningKey(){
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("Power User")
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public <T> T extractClaims(String token, Function<Claims, T> function){
        final Claims claims = extractAllClaims(token);
        return function.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String username = extractUsername(token);

        return (username.equalsIgnoreCase(userDetails.getUsername()) &&
                !isTokenExpired(token));
    }

}