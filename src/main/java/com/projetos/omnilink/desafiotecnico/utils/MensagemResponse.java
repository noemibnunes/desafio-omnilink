package com.projetos.omnilink.desafiotecnico.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MensagemResponse {

    @Schema(description = "Mensagem", example = "{Entidade} {operação realizada} com sucesso.")
    private String mensagem;
}
