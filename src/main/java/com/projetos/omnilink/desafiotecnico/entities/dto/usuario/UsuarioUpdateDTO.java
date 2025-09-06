package com.projetos.omnilink.desafiotecnico.entities.dto.usuario;

import com.projetos.omnilink.desafiotecnico.enums.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioUpdateDTO {
    @Schema(description = "Nome completo do usuário", example = "Joana Silva")
    @NotBlank
    private String nome;

    @Schema(description = "Email do usuário", example = "joana@email.com")
    @NotBlank
    private String email;

    @Schema(description = "Role do usuário", example = "ROLE_CLIENTE")
    @NotBlank
    private RoleEnum role;
}
