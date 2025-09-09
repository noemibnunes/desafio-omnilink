package com.projetos.omnilink.desafiotecnico.services.impl;

import com.projetos.omnilink.desafiotecnico.entities.Cliente;
import com.projetos.omnilink.desafiotecnico.entities.Veiculo;
import com.projetos.omnilink.desafiotecnico.entities.dto.veiculos.VeiculoCreateDTO;
import com.projetos.omnilink.desafiotecnico.entities.dto.veiculos.VeiculoUpdateDTO;
import com.projetos.omnilink.desafiotecnico.exceptions.RegistroDuplicadoException;
import com.projetos.omnilink.desafiotecnico.exceptions.UsuarioNaoEncontradoException;
import com.projetos.omnilink.desafiotecnico.exceptions.VeiculoNaoEncontradoException;
import com.projetos.omnilink.desafiotecnico.repositories.ClienteRepository;
import com.projetos.omnilink.desafiotecnico.repositories.VeiculoRepository;
import com.projetos.omnilink.desafiotecnico.services.VeiculoService;
import com.projetos.omnilink.desafiotecnico.utils.VeiculoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.projetos.omnilink.desafiotecnico.mappers.VeiculoMapper.toEntity;
import static com.projetos.omnilink.desafiotecnico.mappers.VeiculoMapper.updateVeiculoFromDto;

@Service
@RequiredArgsConstructor
public class VeiculoServiceImpl implements VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ClienteRepository clienteRepository;

    @Override
    public void criarVeiculo(VeiculoCreateDTO veiculoDTO) {
        Veiculo veiculo = toEntity(veiculoDTO);

        VeiculoValidator.verificarDadosVeiculo(veiculo);
        verificarDuplicadoPorChassi(veiculo.getChassi());
        veiculoRepository.save(veiculo);
    }

    @Override
    public void editarVeiculo(UUID id, VeiculoUpdateDTO dto) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo não encontrado."));

        updateVeiculoFromDto(veiculo, dto);
        VeiculoValidator.verificarDadosVeiculo(veiculo);
        veiculoRepository.save(veiculo);
    }

    @Override
    public Veiculo criarVeiculoParaCliente(UUID clienteId, VeiculoCreateDTO veiculoDTO) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Cliente não encontrado."));

        Veiculo veiculo = toEntity(veiculoDTO);

        VeiculoValidator.verificarDadosVeiculo(veiculo);
        verificarDuplicadoPorChassi(veiculoDTO.getChassi());

        veiculo = Veiculo.builder()
                .chassi(veiculoDTO.getChassi())
                .marca(veiculoDTO.getMarca())
                .modelo(veiculoDTO.getModelo())
                .ano(veiculoDTO.getAno())
                .tipoVeiculo(veiculoDTO.getTipoVeiculo())
                .tipoCombustivel(veiculoDTO.getTipoCombustivel())
                .quilometragem(veiculoDTO.getQuilometragem())
                .observacoes(veiculoDTO.getObservacoes())
                .build();

        veiculo.setCliente(cliente);
        cliente.getVeiculos().add(veiculo);

        clienteRepository.save(cliente);

        return veiculo;
    }

    @Override
    public Veiculo atualizarClienteDoVeiculo(UUID clienteId, UUID veiculoId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Cliente não encontrado."));

        Veiculo veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo não encontrado."));

        if (veiculo.getCliente() != null) {
            veiculo.getCliente().getVeiculos().remove(veiculo);
        }

        veiculo.setCliente(cliente);
        cliente.getVeiculos().add(veiculo);

        return veiculoRepository.save(veiculo);
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
