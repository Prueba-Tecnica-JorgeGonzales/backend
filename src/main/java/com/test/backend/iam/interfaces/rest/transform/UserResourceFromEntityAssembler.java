package com.test.backend.iam.interfaces.rest.transform;

import com.test.backend.iam.domain.model.aggregates.User;
import com.test.backend.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User entity){
        return new UserResource(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : "N/A"

        );
    }
}
