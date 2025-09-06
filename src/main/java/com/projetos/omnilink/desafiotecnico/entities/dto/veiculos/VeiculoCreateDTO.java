package com.projetos.omnilink.desafiotecnico.entities.dto.veiculos;

import com.projetos.omnilink.desafiotecnico.enums.TipoCombustivelEnum;
import com.projetos.omnilink.desafiotecnico.enums.TipoVeiculoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VeiculoCreateDTO {
    @Schema(description = "Número do Chassi do veículo", example = "1HGCM82633A004352")
    @NotBlank
    private String chassi;

    @Schema(description = "Marca do veículo", example = "Fiat")
    @NotBlank
    private String marca;

    @Schema(description = "Modelo do veículo", example = "Uno")
    @NotBlank
    private String modelo;

    @Schema(description = "Ano do veículo", example = "1999")
    @NotNull
    private int ano;

    @Schema(description = "Tipo de combustível", example = "GASOLINA")
    @NotNull
    private TipoCombustivelEnum tipoCombustivel;

    @Schema(description = "Tipo de veículo", example = "CARRO")
    @NotNull
    private TipoVeiculoEnum tipoVeiculo;

    @Schema(description = "Quilometragem do veículo em quilômetros", example = "45230")
    private int quilometragem;

    @Schema(description = "Observações ou características do veículo", example = "Veículo em bom estado, revisões em dia, único dono")
    private String observacoes;
}
