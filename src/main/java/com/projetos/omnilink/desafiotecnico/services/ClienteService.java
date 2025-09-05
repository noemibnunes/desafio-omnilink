package com.projetos.omnilink.desafiotecnico.services;

import com.projetos.omnilink.desafiotecnico.dto.cliente.ClienteCreateDTO;
import com.projetos.omnilink.desafiotecnico.dto.cliente.ClienteUpdateDTO;
import com.projetos.omnilink.desafiotecnico.entities.Cliente;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClienteService {
    void criarCliente(ClienteCreateDTO clienteDTO);
    void editarCliente(UUID id, ClienteUpdateDTO clienteUpdateDTO);
    Optional<Cliente> buscarClientePorCpf(String cpf);
    List<Cliente> listarClientes();
    void excluirCliente(UUID idCliente);
}
