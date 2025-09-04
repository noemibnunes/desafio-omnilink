package com.projetos.omnilink.desafiotecnico.entities;

import com.projetos.omnilink.desafiotecnico.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "usuarios")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String senha_hash;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "created_at")
    private java.util.Date createdAt;

    @Column(name = "updated_at")
    private java.util.Date updatedAt;
}
