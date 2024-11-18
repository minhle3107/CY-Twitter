package com.global.project.configuration.jwtConfig;

import com.global.project.configuration.AccountDetailsImpl;
import com.global.project.entity.Account;
import com.global.project.repository.AccountRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    @Autowired
    private AccountRepository accountRepository;
    @Value("${jwt.SECRET_ACCESS_TOKEN_KEY}")
    private String JWT_SECRET;
    @Value("${jwt.JWT_EXPIRATION_ACCESS_TOKEN}")
    private int JWT_EXPIRATION;

    public JwtProvider(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String generateToken(AccountDetailsImpl customDetailService) {
        return this.generateTokenByUsername(customDetailService.getUsername());
    }

    public String generateTokenByUsername(String Username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        Account account = accountRepository.findByUsername(Username).get();
        return Jwts.builder()
                .setSubject(Long.toString(account.getId()))
                .claim("username", account.getUsername())
                .claim("role", account.getRole().getName())
                .setExpiration(expiryDate)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public String getKeyByValueFromJWT(String key, String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.get(key, String.class);
    }
}
