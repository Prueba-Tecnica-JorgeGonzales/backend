package com.test.backend.iam.domain.model.commands;

public record UpdateUserCommand(
        Long id,
        String username,
        String email,
        String password
) {
}
