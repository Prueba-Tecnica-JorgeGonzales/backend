package com.test.backend.iam.interfaces.rest.resources;

public record UpdateUserResource(String username,
                                 String email,
                                 String password) {
}
