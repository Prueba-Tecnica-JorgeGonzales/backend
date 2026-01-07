package com.test.backend.iam.interfaces.rest.resources;

public record UserResource(Long id, String username, String email, String password, String createdAt) {
}
