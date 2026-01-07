package com.test.backend.iam.infrastructure.persistence.jpa;

import com.test.backend.iam.domain.model.aggregates.User;
import com.test.backend.iam.domain.model.valueobjects.EmailAddress;
import com.test.backend.iam.domain.model.valueobjects.Username;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
