package com.test.backend.iam.interfaces;

import com.test.backend.iam.domain.model.commands.SignInCommand;
import com.test.backend.iam.domain.model.commands.SignUpCommand;
import com.test.backend.iam.domain.services.UserCommandService;
import com.test.backend.iam.interfaces.rest.resources.AuthenticatedUserResource;
import com.test.backend.iam.interfaces.rest.resources.SignInResource;
import com.test.backend.iam.interfaces.rest.resources.SignUpResource;
import com.test.backend.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import com.test.backend.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import com.test.backend.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserCommandService userCommandService;

    public AuthController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpResource resource) {
        SignUpCommand command = SignUpCommandFromResourceAssembler.toCommandFromResource(resource);
        Long userId = userCommandService.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap("message", "Usuario creado con ID: " + userId));
    }

    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody SignInResource resource) {
        SignInCommand command = SignInCommandFromResourceAssembler.toCommandFromResource(resource);

        var result = userCommandService.handle(command);

        if (result.isEmpty()) return ResponseEntity.notFound().build();

        var userEntity = result.get().getLeft();
        var token = result.get().getRight();
        var authResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(userEntity);
        return ResponseEntity.ok().body(new Object() {
            public final AuthenticatedUserResource user = authResource;
            public final String accessToken = token;
        });
    }
}
