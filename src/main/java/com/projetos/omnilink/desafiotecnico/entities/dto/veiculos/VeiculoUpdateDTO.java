package com.projetos.omnilink.desafiotecnico.entities.dto.veiculos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VeiculoUpdateDTO {
    @Schema(description = "Quilometragem do veículo em quilômetros", example = "45230")
    private int quilometragem;

    @Schema(description = "Observações ou características do veículo", example = "Veículo em bom estado, revisões em dia, único dono")
    @NotBlank
    private String observacoes;
}
