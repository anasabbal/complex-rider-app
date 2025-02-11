package com.verse.gateway.utils;


import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public class JwtUtil {
    private static PublicKey publicKey;

    static {
        try {
            // Load public key from PEM file
            File file = new File("src/main/resources/publickey.pem");
            String keyContent = new String(Files.readAllBytes(file.toPath()))
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s+", ""); // Remove spaces & newlines

            byte[] keyBytes = Base64.getDecoder().decode(keyContent);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load public key", e);
        }
    }

    public static DecodedJWT verifyToken(String token) {
        return JWT.require(Algorithm.RSA256((java.security.interfaces.RSAPublicKey) publicKey, null))
                .build()
                .verify(token);
    }
}
