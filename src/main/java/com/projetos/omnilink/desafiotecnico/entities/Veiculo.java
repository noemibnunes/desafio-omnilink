package com.projetos.omnilink.desafiotecnico.entities;

import com.projetos.omnilink.desafiotecnico.enums.TipoCombustivelEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "O nome da marca é obrigatório")
    private String marca;

    @NotBlank(message = "O nome do modelo é obrigatório")
    private String modelo;

    @NotBlank(message = "O ano é obrigatório")
    private int ano;

    @Enumerated(EnumType.STRING)
    private TipoCombustivelEnum tipoCombustivel;

    private int quilometragem;

    @Size(min = 5, max = 500, message = "A observação deve entre 5 e 500 caracteres")
    @Column(length = 500)
    private String observacoes;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
