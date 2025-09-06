package com.projetos.omnilink.desafiotecnico.services.impl;

import com.projetos.omnilink.desafiotecnico.entities.Cliente;
import com.projetos.omnilink.desafiotecnico.entities.Veiculo;
import com.projetos.omnilink.desafiotecnico.entities.dto.veiculos.VeiculoCreateDTO;
import com.projetos.omnilink.desafiotecnico.entities.dto.veiculos.VeiculoUpdateDTO;
import com.projetos.omnilink.desafiotecnico.exceptions.RegistroDuplicadoException;
import com.projetos.omnilink.desafiotecnico.exceptions.UsuarioNaoEncontradoException;
import com.projetos.omnilink.desafiotecnico.exceptions.VeiculoNaoEncontradoException;
import com.projetos.omnilink.desafiotecnico.mappers.VeiculoMapper;
import com.projetos.omnilink.desafiotecnico.repositories.VeiculoRepository;
import com.projetos.omnilink.desafiotecnico.services.VeiculoService;
import com.projetos.omnilink.desafiotecnico.utils.VeiculoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VeiculoServiceImpl implements VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final VeiculoMapper veiculoMapper;

    @Override
    public void criarVeiculo(VeiculoCreateDTO veiculoDTO) {
        Veiculo veiculo = veiculoMapper.toEntity(veiculoDTO);

        VeiculoValidator.verificarDadosVeiculo(veiculo);
        verificarDuplicadoPorChassi(veiculo.getChassi());
        veiculoRepository.save(veiculo);
    }

    @Override
    public void editarVeiculo(UUID id, VeiculoUpdateDTO dto) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo não encontrado."));

        veiculoMapper.updateVeiculoFromDto(veiculo, dto);
        veiculoRepository.save(veiculo);
    }

    @Override
    public List<Veiculo> listarVeiculos() {
        return veiculoRepository.findAll();
    }

    @Override
    public Veiculo buscarVeiculoPeloChassi(String chassi) {
        return veiculoRepository.findByChassi(chassi)
                .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo não encontrado."));
    }

    @Override
    public void excluirVeiculo(UUID idVeiculo) {
        Veiculo veiculo = veiculoRepository.findById(idVeiculo)
                .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo não encontrado."));

        veiculoRepository.delete(veiculo);
    }

    public void verificarDuplicadoPorChassi(String chassi) {
        boolean existe = veiculoRepository.existsByChassi(chassi);

        if (existe) {
            throw new RegistroDuplicadoException("Já existe um veículo com esse Chassi informado.");
        }
    }

}
