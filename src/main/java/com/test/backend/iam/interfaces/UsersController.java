package com.test.backend.iam.interfaces;

import com.test.backend.iam.domain.model.commands.UpdateUserCommand;
import com.test.backend.iam.domain.model.queries.GetAllUsersQuery;
import com.test.backend.iam.domain.model.queries.GetUserByIdQuery;
import com.test.backend.iam.domain.services.UserCommandService;
import com.test.backend.iam.domain.services.UserQueryService;
import com.test.backend.iam.interfaces.rest.resources.SignUpResource;
import com.test.backend.iam.interfaces.rest.resources.UpdateUserResource;
import com.test.backend.iam.interfaces.rest.resources.UserResource;
import com.test.backend.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import com.test.backend.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    public UsersController(UserQueryService userQueryService, UserCommandService userCommandService) {
        this.userQueryService = userQueryService;
        this.userCommandService = userCommandService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody SignUpResource resource) {
        // Reutilizamos el assembler y el servicio de comando
        var command = SignUpCommandFromResourceAssembler.toCommandFromResource(resource);
        Long id = userCommandService.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping
    public ResponseEntity<List<UserResource>> getAllUsers() {
        var query = new GetAllUsersQuery();
        var users = userQueryService.handle(query);

        var resources = users.stream()
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long id) {
        var query = new GetUserByIdQuery(id);
        var user = userQueryService.handle(query);

        return user.map(entity -> ResponseEntity.ok(UserResourceFromEntityAssembler.toResourceFromEntity(entity)))
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @RequestBody UpdateUserResource resource) {

        var command = new UpdateUserCommand(
                id,
                resource.username(),
                resource.email(),
                resource.password()
        );
        var updatedUser = userCommandService.handleUpdate(command);
        return updatedUser.map(user -> ResponseEntity.ok(
                UserResourceFromEntityAssembler.toResourceFromEntity(user))
        ).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userCommandService.handleDelete(id);
        return ResponseEntity.noContent().build();
    }
}
