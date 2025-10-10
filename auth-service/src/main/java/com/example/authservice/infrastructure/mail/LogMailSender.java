package com.example.authservice.infrastructure.mail;

import com.example.authservice.application.ports.MailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Profile({"local", "dev", "test"})
public class LogMailSender implements MailSender {
    private static final Logger log = LoggerFactory.getLogger(LogMailSender.class);

    @Override
    public void sendMagicLink(String to, String magicUrl, Instant expiresAt) {
        log.info("[LOCAL] Magic link para {}: {} (expira em {})", to, magicUrl, expiresAt);
    }
}
