package com.example.authservice.domain.auth.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class HashedToken {

    @Column(name = "hashed_token", nullable = false)
    private String value;

    public HashedToken(String value) {
        String clearValue = value == null ? null : value.trim();

        if (clearValue == null || clearValue.isBlank()) {
            throw new IllegalArgumentException("Token invalido");
        }

        this.value = value;
    }

    public static HashedToken of(String value) {
        return new HashedToken(value);
    }
}
