package com.test.backend.iam.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Embeddable
public record Password(
        @NotBlank
        @Size(max = 120)
        String password
) {
    public Password() { this(null); }
}
