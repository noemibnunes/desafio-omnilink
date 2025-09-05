package com.projetos.omnilink.desafiotecnico.entities;

import com.projetos.omnilink.desafiotecnico.enums.TipoCombustivelEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Table(name = "veiculos")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private String marca;
    private String modelo;
    private int ano;

    @Enumerated(EnumType.STRING)
    private TipoCombustivelEnum tipoCombustivel;

    private int quilometragem;

    @Column(length = 500)
    private String observacoes;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
