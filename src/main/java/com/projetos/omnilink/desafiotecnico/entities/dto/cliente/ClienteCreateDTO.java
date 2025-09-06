package com.projetos.omnilink.desafiotecnico.entities.dto.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteCreateDTO {
    @Schema(description = "Nome completo do cliente", example = "Joana Silva")
    @NotBlank
    private String nome;

    @Schema(description = "Email do cliente", example = "joana@email.com")
    @NotBlank
    private String email;

    @Schema(description = "CPF do cliente", example = "123.456.789-01")
    @NotBlank
    private String cpf;

    @Schema(description = "Data de nascimento do cliente", example = "1995-08-20")
    private LocalDate dataNascimento;
}
