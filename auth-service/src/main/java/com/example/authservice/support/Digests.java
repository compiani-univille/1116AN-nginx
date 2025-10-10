package com.example.authservice.support;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Digests {
    public static String sha256Hex(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] d = md.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder(d.length * 2);
            for (byte b : d) sb.append(String.format("%02x", b));

            return sb.toString();
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao calcular SHA-256", e);
        }
    }
}
