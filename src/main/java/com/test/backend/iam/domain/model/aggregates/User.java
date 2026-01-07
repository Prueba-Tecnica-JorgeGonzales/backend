package com.test.backend.iam.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios") // Coincide con tu tabla SQL
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder // Patrón Builder para crear objetos fácil
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // Aquí guardaremos el Hash, no el texto plano

    @Builder.Default
    private String estado = "ACTIVO";

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @PrePersist // Se ejecuta automáticamente antes de guardar
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
