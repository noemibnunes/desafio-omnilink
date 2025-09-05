package com.projetos.omnilink.desafiotecnico.services;

import com.projetos.omnilink.desafiotecnico.entities.dto.cliente.ClienteCreateDTO;
import com.projetos.omnilink.desafiotecnico.entities.dto.cliente.ClienteUpdateDTO;
import com.projetos.omnilink.desafiotecnico.entities.Cliente;

import java.util.List;
import java.util.UUID;

public interface ClienteService {
    void criarCliente(ClienteCreateDTO clienteDTO);
    void editarCliente(UUID id, ClienteUpdateDTO clienteUpdateDTO);
    Cliente buscarClientePorCpf(String cpf);
    List<Cliente> listarClientes();
    void excluirCliente(UUID idCliente);
}
