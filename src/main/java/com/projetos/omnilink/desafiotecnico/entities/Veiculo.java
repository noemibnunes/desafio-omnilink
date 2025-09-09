package com.projetos.omnilink.desafiotecnico.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.projetos.omnilink.desafiotecnico.enums.TipoCombustivelEnum;
import com.projetos.omnilink.desafiotecnico.enums.TipoVeiculoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Table(name = "veiculos")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Veiculo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonBackReference
    private Cliente cliente;

    @Size(max = 17, message = "O número do Chassi deve conter 17 caracteres")
    @NotBlank(message = "O número do Chassi é obrigatório")
    private String chassi;

    @NotBlank(message = "O nome da marca é obrigatório")
    private String marca;

    @NotBlank(message = "O nome do modelo é obrigatório")
    private String modelo;

    @NotNull(message = "O ano é obrigatório")
    private int ano;

    @Enumerated(EnumType.STRING)
    private TipoCombustivelEnum tipoCombustivel;

    @Enumerated(EnumType.STRING)
    private TipoVeiculoEnum tipoVeiculo;

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
