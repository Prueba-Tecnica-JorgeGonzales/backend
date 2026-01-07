package com.test.backend.iam.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Embeddable
public record EmailAddress(
        @NotBlank
        @Size(max = 50)
        @Email
        String email
) {
        public EmailAddress() { this(null); }
}
