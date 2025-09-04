package com.projetos.omnilink.desafiotecnico.entities;

import com.projetos.omnilink.desafiotecnico.enums.TipoCombustivel;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "veiculos")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private String marca;
    private String modelo;
    private int ano;

    @Enumerated(EnumType.STRING)
    private TipoCombustivel tipo_combustivel;

    private int quilometragem;

    @Column(length = 500)
    private String observacoes;

    @Column(name = "created_at")
    private java.util.Date createdAt;

    @Column(name = "updated_at")
    private java.util.Date updatedAt;
}
