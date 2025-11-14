package com.example.authservice.messaging.events;

import java.util.UUID;

public record UserCreatedEvent(
    UUID UserId,
    String name,
    String email,
    String role
) {}
