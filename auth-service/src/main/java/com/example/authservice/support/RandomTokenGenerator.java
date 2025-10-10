package com.example.authservice.support;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomTokenGenerator {
    private static final SecureRandom random = new SecureRandom();

    public static String urlSafeToken(int length) {
        byte[] bytes = new byte[length];

        random.nextBytes(bytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
