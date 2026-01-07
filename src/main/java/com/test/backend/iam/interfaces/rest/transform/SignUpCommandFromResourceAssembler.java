package com.test.backend.iam.interfaces.rest.transform;

import com.test.backend.iam.domain.model.commands.SignUpCommand;
import com.test.backend.iam.interfaces.rest.resources.SignUpResource;

public class SignUpCommandFromResourceAssembler {

    public static SignUpCommand toCommandFromResource(SignUpResource resource){
        return new SignUpCommand(
                resource.username(),
                resource.email(),
                resource.password()
        );
    }
}
