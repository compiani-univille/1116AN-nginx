package com.example.authservice.interfaces.rest;

import com.example.authservice.application.auth.PasswordLoginHandler;
import com.example.authservice.application.auth.RequestMagicLinkHandler;
import com.example.authservice.application.auth.VerifyMagicLinkHandler;
import com.example.authservice.interfaces.rest.dto.auth.MagicLinkRequest;
import com.example.authservice.interfaces.rest.dto.auth.PasswordLoginRequest;
import com.example.authservice.interfaces.rest.dto.auth.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final PasswordLoginHandler passwordLoginHandler;
    private final RequestMagicLinkHandler requestMagicLinkHandler;
    private final VerifyMagicLinkHandler verifyMagicLinkHandler;

    @PostMapping("/login/password")
    public ResponseEntity<TokenResponse> loginWithPassword(@Valid @RequestBody PasswordLoginRequest request) {
        TokenResponse token = passwordLoginHandler.handle(
            request.email(),
            request.password()
        );

        return ResponseEntity.ok(token);
    }

    @PostMapping("/login/magic")
    public ResponseEntity<Void> requstMagicLink(@Valid @RequestBody MagicLinkRequest req) {
        requestMagicLinkHandler.handle(req.email());
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/login/magic/verify")
    public ResponseEntity<TokenResponse> verifyMagicLink(@RequestParam String token) {
        TokenResponse tokenResponse = verifyMagicLinkHandler.handle(token);
        return ResponseEntity.ok(tokenResponse);
    }
}
