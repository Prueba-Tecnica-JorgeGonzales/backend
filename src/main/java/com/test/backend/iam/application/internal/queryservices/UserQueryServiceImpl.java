package com.test.backend.iam.application.internal.queryservices;

import com.test.backend.iam.domain.model.aggregates.User;
import com.test.backend.iam.domain.model.queries.GetAllUsersQuery;
import com.test.backend.iam.domain.model.queries.GetUserByIdQuery;
import com.test.backend.iam.domain.model.queries.GetUserByUsernameQuery;
import com.test.backend.iam.domain.services.UserQueryService;
import com.test.backend.iam.infrastructure.persistence.jpa.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> handle(GetUserByUsernameQuery query) {
        return this.userRepository.findByUsername(query.username().username());
    }
    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return this.userRepository.findById(query.id());
    }
    @Override
    public List<User> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }
}
