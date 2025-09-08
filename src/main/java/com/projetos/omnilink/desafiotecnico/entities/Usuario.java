package com.projetos.omnilink.desafiotecnico.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projetos.omnilink.desafiotecnico.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Table(name = "usuarios")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "O CPF é obrigatório")
    @Size(max = 14, message = "O CPF deve ter 11 caracteres")
    @Column(unique = true, length = 14)
    private String cpf;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Email(message = "Email inválido")
    @NotBlank(message = "O email é obrigatório")
    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String senha_hash;

    @NotNull(message = "Role é obrigatória")
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cliente_id")
    @JsonBackReference
    private Cliente cliente;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
