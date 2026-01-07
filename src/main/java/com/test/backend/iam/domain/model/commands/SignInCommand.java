package com.test.backend.iam.domain.model.commands;

public record SignInCommand(
        String username,
        String password
) {
}
