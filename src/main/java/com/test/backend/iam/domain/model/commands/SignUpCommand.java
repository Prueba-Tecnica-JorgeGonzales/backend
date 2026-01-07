package com.test.backend.iam.domain.model.commands;

public record SignUpCommand(
        String username,
        String email,
        String password
) {
}
