package com.projetos.omnilink.desafiotecnico.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Table(name = "clientes")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;

    @Column(unique = true)
    private String email;

    @Column(unique = true, length = 14)
    private String cpf;

    @Temporal(TemporalType.DATE)
    private Date data_nascimento;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Veiculo> veiculos;

    @OneToOne(mappedBy = "cliente")
    private Usuario usuario;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    // getters e setters
}
