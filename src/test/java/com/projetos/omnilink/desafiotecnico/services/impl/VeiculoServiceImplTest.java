package com.projetos.omnilink.desafiotecnico.services.impl;

import com.projetos.omnilink.desafiotecnico.entities.Cliente;
import com.projetos.omnilink.desafiotecnico.entities.Veiculo;
import com.projetos.omnilink.desafiotecnico.entities.dto.veiculos.VeiculoCreateDTO;
import com.projetos.omnilink.desafiotecnico.entities.dto.veiculos.VeiculoUpdateDTO;
import com.projetos.omnilink.desafiotecnico.enums.TipoCombustivelEnum;
import com.projetos.omnilink.desafiotecnico.enums.TipoVeiculoEnum;
import com.projetos.omnilink.desafiotecnico.exceptions.RegistroDuplicadoException;
import com.projetos.omnilink.desafiotecnico.exceptions.VeiculoNaoEncontradoException;
import com.projetos.omnilink.desafiotecnico.mappers.VeiculoMapper;
import com.projetos.omnilink.desafiotecnico.repositories.ClienteRepository;
import com.projetos.omnilink.desafiotecnico.repositories.VeiculoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class VeiculoServiceImplTest {

    @InjectMocks
    private VeiculoServiceImpl veiculoService;

    @Mock
    private VeiculoMapper veiculoMapper;

    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve salvar um novo veículo com sucesso")
    public void deveSalvarNovoVeiculo() {
        VeiculoCreateDTO dto = getVeiculoDto();
        Veiculo veiculo = getVeiculo();

        when(veiculoMapper.toEntity(dto)).thenReturn(veiculo);
        when(veiculoRepository.save(Mockito.any(Veiculo.class))).thenAnswer(i -> i.getArguments()[0]);

        veiculoService.criarVeiculo(dto);

        verify(veiculoRepository, Mockito.times(1)).save(Mockito.any(Veiculo.class));

        Assertions.assertEquals("1HGCM82633A004352", veiculo.getChassi());
        Assertions.assertEquals("marca", veiculo.getMarca());
        Assertions.assertEquals("modelo", veiculo.getModelo());
    }

    @Test
    @DisplayName("Deve atualizar informações do veículo (Quilometragem e observações)")
    public void deveAtualizarVeiculo() {
        Veiculo veiculo = getVeiculo();
        VeiculoUpdateDTO dto = getVeiculoUdpateDto();

        when(veiculoRepository.findById(veiculo.getId()))
                .thenReturn(Optional.of(veiculo));

        when(veiculoRepository.save(Mockito.any(Veiculo.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        veiculoService.editarVeiculo(veiculo.getId(), dto);

        verify(veiculoRepository, Mockito.times(1)).save(Mockito.any(Veiculo.class));

        Assertions.assertEquals(dto.getQuilometragem(), veiculo.getQuilometragem());
        Assertions.assertEquals(dto.getObservacoes(), veiculo.getObservacoes());

        Assertions.assertEquals("1HGCM82633A004352", veiculo.getChassi());
    }

    @Test
    @DisplayName("Deve salvar um novo veículo para o cliente informado")
    public void deveSalvarNovoVeiculoParaCliente() {
        UUID clienteId = UUID.randomUUID();

        VeiculoCreateDTO dto = getVeiculoDto();
        Veiculo veiculo = getVeiculo();
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setVeiculos(new ArrayList<>());

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        when(veiculoMapper.toEntity(dto)).thenReturn(veiculo);

        when(veiculoRepository.save(Mockito.any(Veiculo.class))).thenAnswer(i -> i.getArguments()[0]);

        Veiculo veiculoSalvo = veiculoService.criarVeiculoParaCliente(clienteId, dto);

        verify(clienteRepository, times(1)).save(cliente);
        verify(veiculoRepository, never()).save(Mockito.any());

        Assertions.assertEquals("1HGCM82633A004352", veiculoSalvo.getChassi());
        Assertions.assertEquals("marca", veiculoSalvo.getMarca());
        Assertions.assertEquals("modelo", veiculoSalvo.getModelo());

        Assertions.assertEquals(cliente, veiculoSalvo.getCliente());
        Assertions.assertTrue(cliente.getVeiculos().contains(veiculoSalvo));
    }


    @Test
    @DisplayName("Deve retornar uma lista de veículos")
    public void deveRetornarListaVeiculos() {
        veiculoService.listarVeiculos();

        Assertions.assertNotNull(veiculoRepository.findAll());
    }

    @Test
    @DisplayName("Deve retornar o veículo correspondente ao Chassi informado para busca")
    public void deveRetornarVeiculoCorrespondenteAoChassi() {
        Veiculo veiculoMock = getVeiculo();

        when(veiculoRepository.findByChassi(veiculoMock.getChassi()))
                .thenReturn(Optional.of(veiculoMock));

        Veiculo veiculo = veiculoService.buscarVeiculoPeloChassi(veiculoMock.getChassi());

        Assertions.assertEquals(veiculoMock.getChassi(), veiculo.getChassi());

        verify(veiculoRepository, Mockito.times(1)).findByChassi(veiculoMock.getChassi());
    }

    @Test
    @DisplayName("Deve lançar exceção quando Chassi não for encontrado")
    public void deveLancarExcecaoQuandoChassiNaoEncontrado() {
        String chassi = "1HGCM82633A00435";
        when(veiculoRepository.findByChassi(chassi)).thenReturn(Optional.empty());

        Assertions.assertThrows(VeiculoNaoEncontradoException.class,
                () -> veiculoService.buscarVeiculoPeloChassi(chassi));

        verify(veiculoRepository, Mockito.times(1)).findByChassi(chassi);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o Chassi já possuir cadastro")
    public void deveRetornarExceptionChassiPossuiCadastro() {
        String chassi = "1HGCM82633A004352";
        when(veiculoRepository.existsByChassi(chassi)).thenReturn(true);

        RegistroDuplicadoException exception = Assertions.assertThrows(
                RegistroDuplicadoException.class,
                () -> veiculoService.verificarDuplicadoPorChassi(chassi)
        );

        Assertions.assertEquals("Já existe um veículo com esse Chassi informado.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve excluir veículo correspondente ao ID informado")
    public void deveExcluirVeiculo() {
        Veiculo veiculo = getVeiculo();

        when(veiculoRepository.findById(veiculo.getId())).thenReturn(Optional.of(veiculo));

        veiculoService.excluirVeiculo(veiculo.getId());

        verify(veiculoRepository, Mockito.times(1)).findById(veiculo.getId());
        verify(veiculoRepository, Mockito.times(1)).delete(veiculo);
    }


    @Test
    @DisplayName("Deve lançar exceção ao tentar excluir veículo inexistente")
    public void deveLancarExcecaoAoExcluirVeiculoInexistente() {
        UUID idVeiculo = UUID.randomUUID();

        when(veiculoRepository.findById(idVeiculo)).thenReturn(Optional.empty());

        Assertions.assertThrows(VeiculoNaoEncontradoException.class,
                () -> veiculoService.excluirVeiculo(idVeiculo));

        verify(veiculoRepository, Mockito.times(1)).findById(idVeiculo);
        verify(veiculoRepository, Mockito.never()).delete(Mockito.any());
    }

    Veiculo getVeiculo() {
        UUID idVeiculo = UUID.randomUUID();

        return Veiculo.builder()
                .id(idVeiculo)
                .chassi("1HGCM82633A004352")
                .marca("marca")
                .modelo("modelo")
                .ano(2000)
                .tipoVeiculo(TipoVeiculoEnum.CARRO)
                .tipoCombustivel(TipoCombustivelEnum.GASOLINA)
                .quilometragem(0)
                .observacoes(null)
                .build();
    }

    VeiculoCreateDTO getVeiculoDto() {
        VeiculoCreateDTO veiculoCreateDTO = new VeiculoCreateDTO();

        veiculoCreateDTO.setChassi(getVeiculo().getChassi());
        veiculoCreateDTO.setMarca(getVeiculo().getMarca());
        veiculoCreateDTO.setModelo(getVeiculo().getModelo());
        veiculoCreateDTO.setAno(getVeiculo().getAno());
        veiculoCreateDTO.setTipoVeiculo(getVeiculo().getTipoVeiculo());
        veiculoCreateDTO.setTipoCombustivel(getVeiculo().getTipoCombustivel());
        veiculoCreateDTO.setQuilometragem(0);
        veiculoCreateDTO.setObservacoes(null);

        return veiculoCreateDTO;
    }

    VeiculoUpdateDTO getVeiculoUdpateDto() {
        VeiculoUpdateDTO veiculoUdpateDto = new VeiculoUpdateDTO();

        veiculoUdpateDto.setQuilometragem(getVeiculo().getQuilometragem());
        veiculoUdpateDto.setObservacoes(getVeiculo().getObservacoes());

        return veiculoUdpateDto;
    }
}