package com.projetos.omnilink.desafiotecnico.entities.dto.usuario;

import com.projetos.omnilink.desafiotecnico.enums.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioCreateDTO {
    @Schema(description = "Nome completo do usuário", example = "Joana Silva")
    @NotBlank
    private String nome;

    @Schema(description = "CPF do usuário", example = "123.456.789-01")
    @NotBlank
    private String cpf;

    @Schema(description = "Email do usuário", example = "joana@email.com")
    @NotBlank
    private String email;

    @Schema(description = "Senha do usuário", example = "s3nh@")
    @NotBlank
    private String senha;

    @Schema(description = "Role do usuário", example = "CLIENTE")
    @NotBlank
    private RoleEnum role;
}
