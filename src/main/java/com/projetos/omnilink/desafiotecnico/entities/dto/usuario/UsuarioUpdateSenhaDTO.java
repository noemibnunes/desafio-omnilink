package com.projetos.omnilink.desafiotecnico.entities.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UsuarioUpdateSenhaDTO {

    @Schema(description = "Senha", example = "Minh@s3nha")
    private String senha;
}
