package com.projetos.omnilink.desafiotecnico.services.impl;

import com.projetos.omnilink.desafiotecnico.entities.dto.cliente.ClienteCreateDTO;
import com.projetos.omnilink.desafiotecnico.exceptions.DadosInvalidosException;
import com.projetos.omnilink.desafiotecnico.mappers.ClienteMapper;
import com.projetos.omnilink.desafiotecnico.repositories.ClienteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Test
    @DisplayName("Deve retornar uma lista de clientes")
    public void deveRetornarListaClientes() {
        clienteService.listarClientes();

        Assertions.assertNotNull(clienteRepository.findAll());
    }
}