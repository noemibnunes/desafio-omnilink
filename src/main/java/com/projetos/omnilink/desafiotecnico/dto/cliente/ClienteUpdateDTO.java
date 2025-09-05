package com.projetos.omnilink.desafiotecnico.dto.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteUpdateDTO {

    @Schema(description = "Nome completo do cliente", example = "Joana Silva")
    private String nome;

    @Schema(description = "Email do cliente", example = "joana@email.com")
    private String email;

    @Schema(description = "Data de nascimento do cliente", example = "1995-08-20")
    private LocalDate dataNascimento;
}
