package com.test.backend.iam.domain.services;

import com.test.backend.iam.domain.model.aggregates.User;
import com.test.backend.iam.domain.model.commands.SignInCommand;
import com.test.backend.iam.domain.model.commands.SignUpCommand;
import com.test.backend.iam.domain.model.commands.UpdateUserCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserCommandService {
    Long handle(SignUpCommand command);
    Optional<ImmutablePair<User, String>> handle(SignInCommand command);

    // Agregados para el CRUD completo
    Optional<User> handleUpdate(UpdateUserCommand command);
    void handleDelete(Long id);
}
