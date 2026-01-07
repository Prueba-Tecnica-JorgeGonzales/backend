package com.test.backend.iam.application.internal.commandservices;

import com.test.backend.iam.application.internal.outboundservices.hashing.HashingService;
import com.test.backend.iam.domain.model.aggregates.User;
import com.test.backend.iam.domain.model.commands.SignInCommand;
import com.test.backend.iam.domain.model.commands.SignUpCommand;
import com.test.backend.iam.domain.model.commands.UpdateUserCommand;
import com.test.backend.iam.domain.model.valueobjects.EmailAddress;
import com.test.backend.iam.domain.model.valueobjects.Username;
import com.test.backend.iam.domain.services.UserCommandService;
import com.test.backend.iam.infrastructure.persistence.jpa.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
    }


    @Override
    public Long handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        if (userRepository.existsByEmail(command.email())) {
            throw new RuntimeException("El correo electrónico ya existe");
        }

        String hashedPassword = hashingService.encode(command.password());
        User user = User.builder()
                .username(command.username())
                .email(command.email())
                .password(hashedPassword)
                .estado("ACTIVO")
                .build();

        userRepository.save(user);
        return user.getId();
    }

    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var userOptional = userRepository.findByUsername(command.username());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        User user = userOptional.get();

        if (!hashingService.matches(command.password(), user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String token = UUID.randomUUID().toString();

        return Optional.of(new ImmutablePair<>(user, token));
    }

    public Optional<User> handleUpdate(UpdateUserCommand command) {
        var userOptional = userRepository.findById(command.id());

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        User user = userOptional.get();

        if (command.username() != null && !command.username().isBlank()) {
            user.setUsername(command.username());
        }

        if (command.email() != null && !command.email().isBlank()) {
            user.setEmail(command.email());
        }

        if (command.password() != null && !command.password().isBlank()) {
            String newHashedPassword = hashingService.encode(command.password());
            user.setPassword(newHashedPassword);
        }

        userRepository.save(user);
        return Optional.of(user);
    }

    public void handleDelete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }


}
