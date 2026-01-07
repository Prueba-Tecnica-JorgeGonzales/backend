package com.test.backend.iam.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Embeddable
public record Username(
        @NotBlank
        @Size(max = 20)
        String username
) {
    public Username() {
        this(null);
    }
}
