package com.projetos.omnilink.desafiotecnico.mappers;

import com.projetos.omnilink.desafiotecnico.entities.Usuario;
import com.projetos.omnilink.desafiotecnico.entities.dto.usuario.UsuarioCreateDTO;
import com.projetos.omnilink.desafiotecnico.entities.dto.usuario.UsuarioUpdateDTO;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public static Usuario toEntity(UsuarioCreateDTO dto) {
        return Usuario.builder()
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .email(dto.getEmail())
                .role(dto.getRole())
                .build();
    }

    public static void updateUsuarioFromDto(Usuario usuario, UsuarioUpdateDTO dto) {
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setRole(dto.getRole());
    }}
