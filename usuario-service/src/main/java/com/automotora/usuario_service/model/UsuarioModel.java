package com.automotora.usuario_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioModel {

    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String rol;

    @PrePersist
    public void generarId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }
}