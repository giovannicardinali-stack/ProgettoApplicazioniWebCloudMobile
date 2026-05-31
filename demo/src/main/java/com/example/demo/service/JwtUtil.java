package com.example.demo.service;

import com.example.demo.entity.Ruolo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;


public class JwtUtil {
    private static final String secret = "123456chiaveSegretaDiProva123456";

    private static final Key key = Keys.hmacShaKeyFor(secret.getBytes());



    //Genera token e ne specifica la data di creazione e di scadenza. Inoltre lo firma
    //la chiave segreta
    public static String generateToken(String username, String ruolo) {
        return Jwts.builder()
                .setSubject(username).claim("role", ruolo)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    //decodifica il token verificandone la firma e ne legge il contenuto
    public static String extractUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    //controlla se il token è valido
    public static boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

//    public static String extractRole(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .get("role", String.class);
//    }
}
