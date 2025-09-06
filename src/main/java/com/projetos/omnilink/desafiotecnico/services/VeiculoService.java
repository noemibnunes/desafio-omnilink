package com.projetos.omnilink.desafiotecnico.services;

import com.projetos.omnilink.desafiotecnico.entities.Veiculo;
import com.projetos.omnilink.desafiotecnico.entities.dto.veiculos.VeiculoCreateDTO;
import com.projetos.omnilink.desafiotecnico.entities.dto.veiculos.VeiculoUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface VeiculoService {
    void criarVeiculo(VeiculoCreateDTO veiculoDTO);
    void editarVeiculo(UUID id, VeiculoUpdateDTO veiculoUpdateDTO);
    List<Veiculo> listarVeiculos();
    Veiculo buscarVeiculoPeloChassi(String chassi);
    void excluirVeiculo(UUID idVeiculo);
    Veiculo criarVeiculoParaCliente(UUID clienteId, VeiculoCreateDTO veiculoDTO);
}
