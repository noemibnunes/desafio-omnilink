package com.projetos.omnilink.desafiotecnico.services.impl;

import com.projetos.omnilink.desafiotecnico.entities.Cliente;
import com.projetos.omnilink.desafiotecnico.entities.Usuario;
import com.projetos.omnilink.desafiotecnico.entities.dto.cliente.ClienteCreateDTO;
import com.projetos.omnilink.desafiotecnico.entities.dto.cliente.ClienteUpdateDTO;
import com.projetos.omnilink.desafiotecnico.exceptions.UsuarioNaoEncontradoException;
import com.projetos.omnilink.desafiotecnico.mappers.ClienteMapper;
import com.projetos.omnilink.desafiotecnico.repositories.ClienteRepository;
import com.projetos.omnilink.desafiotecnico.repositories.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClienteServiceImplTest {

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Mock
    private ClienteMapper clienteMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve salvar um novo cliente com sucesso")
    public void deveSalvarNovoCliente() {
        ClienteCreateDTO dto = getClienteDto();
        Cliente cliente = getCliente();

        when(clienteMapper.toEntity(dto)).thenReturn(cliente);
        when(clienteRepository.save(Mockito.any(Cliente.class))).thenAnswer(i -> i.getArguments()[0]);
        when(usuarioRepository.save(Mockito.any(Usuario.class))).thenAnswer(i -> i.getArguments()[0]);
        when(passwordEncoder.encode(Mockito.anyString())).thenReturn("senhaHash");

        clienteService.criarCliente(dto);

        verify(clienteRepository, Mockito.times(2)).save(Mockito.any(Cliente.class));
        verify(usuarioRepository, Mockito.times(1)).save(Mockito.any(Usuario.class));

        Assertions.assertEquals("Teste", cliente.getNome());
        Assertions.assertEquals("teste@gmail.com", cliente.getEmail());
        Assertions.assertEquals("11111111111", cliente.getCpf());
    }

    @Test
    @DisplayName("Deve atualizar informações do cliente")
    public void deveAtualizarCliente() {
        Cliente cliente = getCliente();
        ClienteUpdateDTO dto = getClienteUdpateDto();

        clienteMapper.updateClienteFromDto(cliente, dto);

        when(clienteRepository.findById(cliente.getId()))
                .thenReturn(Optional.of(cliente));

        when(clienteRepository.save(Mockito.any(Cliente.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        clienteService.editarCliente(cliente.getId(), dto);

        verify(clienteRepository, Mockito.times(1)).save(cliente);

        Assertions.assertEquals(dto.getNome(), cliente.getNome());
        Assertions.assertEquals(dto.getEmail(), cliente.getEmail());
        Assertions.assertEquals(dto.getDataNascimento(), cliente.getDataNascimento());

        Assertions.assertEquals("11111111111", cliente.getCpf());
    }

    @Test
    @DisplayName("Deve retornar uma lista de clientes")
    public void deveRetornarListaClientes() {
        clienteService.listarClientes();

        Assertions.assertNotNull(clienteRepository.findAll());
    }

    @Test
    @DisplayName("Deve retornar o cliente correspondente ao CPF informado para busca")
    public void deveRetornarClienteCorrespondenteAOCPF() {
        Cliente clienteMock = getCliente();

        when(clienteRepository.findByCpf(clienteMock.getCpf()))
                .thenReturn(Optional.of(clienteMock));

        Cliente cliente = clienteService.buscarClientePorCpf(clienteMock.getCpf());

        Assertions.assertEquals(clienteMock.getCpf(), cliente.getCpf());

        verify(clienteRepository, Mockito.times(1)).findByCpf(clienteMock.getCpf());
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF não for encontrado")
    public void deveLancarExcecaoQuandoCPFNaoEncontrado() {
        String cpf = "999999999";
        when(clienteRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        Assertions.assertThrows(UsuarioNaoEncontradoException.class,
                () -> clienteService.buscarClientePorCpf(cpf));

        verify(clienteRepository, Mockito.times(1)).findByCpf(cpf);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o CPF já possuir cadastro")
    public void deveRetornarExceptionCPFPossuiCadastro() {
        String cpf = "123456789";
        when(clienteRepository.existsByCpf(cpf)).thenReturn(true);

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clienteService.verificarDuplicadoPorCpf(cpf)
        );

        Assertions.assertEquals("Já existe um cliente com esse CPF informado.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve excluir cliente correspondente ao ID informado")
    public void deveExcluirCliente() {
        Cliente cliente = getCliente();

        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        clienteService.excluirCliente(cliente.getId());

        verify(clienteRepository, Mockito.times(1)).findById(cliente.getId());
        verify(usuarioRepository, Mockito.never()).delete(Mockito.any());
        verify(clienteRepository, Mockito.times(1)).delete(cliente);
    }


    @Test
    @DisplayName("Deve lançar exceção ao tentar excluir cliente inexistente")
    public void deveLancarExcecaoAoExcluirClienteInexistente() {
        UUID idCliente = UUID.randomUUID();

        when(clienteRepository.findById(idCliente)).thenReturn(Optional.empty());

        Assertions.assertThrows(UsuarioNaoEncontradoException.class,
                () -> clienteService.excluirCliente(idCliente));

        verify(clienteRepository, Mockito.times(1)).findById(idCliente);
        verify(usuarioRepository, Mockito.never()).delete(Mockito.any());
        verify(clienteRepository, Mockito.never()).delete(Mockito.any());
    }

    Cliente getCliente() {
        UUID idCliente = UUID.randomUUID();

        return Cliente.builder()
                .id(idCliente)
                .nome("Teste")
                .email("teste@gmail.com")
                .cpf("11111111111")
                .dataNascimento(LocalDate.of(2000, 1,1))
                .build();
    }

    ClienteCreateDTO getClienteDto() {
        ClienteCreateDTO clienteCreateDTO = new ClienteCreateDTO();

        clienteCreateDTO.setNome(getCliente().getNome());
        clienteCreateDTO.setEmail(getCliente().getEmail());
        clienteCreateDTO.setCpf(getCliente().getCpf());
        clienteCreateDTO.setDataNascimento(getCliente().getDataNascimento());

        return clienteCreateDTO;
    }

    ClienteUpdateDTO getClienteUdpateDto() {
        ClienteUpdateDTO clienteUdpateDto = new ClienteUpdateDTO();

        clienteUdpateDto.setNome(getCliente().getNome());
        clienteUdpateDto.setEmail(getCliente().getEmail());
        clienteUdpateDto.setDataNascimento(getCliente().getDataNascimento());

        return clienteUdpateDto;
    }
}