package com.projetos.omnilink.desafiotecnico.mappers;

import com.projetos.omnilink.desafiotecnico.entities.dto.cliente.ClienteCreateDTO;
import com.projetos.omnilink.desafiotecnico.entities.dto.cliente.ClienteUpdateDTO;
import com.projetos.omnilink.desafiotecnico.entities.Cliente;
import com.projetos.omnilink.desafiotecnico.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteCreateDTO dto) {
        return Cliente.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .cpf(dto.getCpf())
                .dataNascimento(dto.getDataNascimento())
                .build();
    }

    public void updateClienteFromDto(Cliente cliente, ClienteUpdateDTO dto) {
        if (dto.getNome() != null) cliente.setNome(dto.getNome());
        if (dto.getEmail() != null) cliente.setEmail(dto.getEmail());
        if (dto.getDataNascimento() != null) cliente.setDataNascimento(dto.getDataNascimento());
    }

    public void updateUsuarioFromCliente(Usuario usuario, ClienteUpdateDTO dto) {
        if (dto.getNome() != null) usuario.setNome(dto.getNome());
        if (dto.getEmail() != null) usuario.setEmail(dto.getEmail());
    }
}
