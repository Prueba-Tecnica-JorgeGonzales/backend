package com.test.backend.iam.interfaces.rest.transform;

import com.test.backend.iam.domain.model.aggregates.User;
import com.test.backend.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user){
        return new AuthenticatedUserResource(user.getId(), user.getUsername(), user.getEmail());
    }
}
