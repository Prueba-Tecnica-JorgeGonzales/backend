package com.test.backend.iam.domain.model.queries;

import com.test.backend.iam.domain.model.valueobjects.Username;

public record GetUserByUsernameQuery(Username username) {
}
