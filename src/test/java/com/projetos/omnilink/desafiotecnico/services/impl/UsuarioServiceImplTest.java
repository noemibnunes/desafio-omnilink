package com.projetos.omnilink.desafiotecnico.services.impl;

import com.projetos.omnilink.desafiotecnico.entities.Usuario;
import com.projetos.omnilink.desafiotecnico.entities.dto.usuario.UsuarioCreateDTO;
import com.projetos.omnilink.desafiotecnico.entities.dto.usuario.UsuarioUpdateDTO;
import com.projetos.omnilink.desafiotecnico.entities.dto.usuario.UsuarioUpdateSenhaDTO;
import com.projetos.omnilink.desafiotecnico.enums.RoleEnum;
import com.projetos.omnilink.desafiotecnico.exceptions.DadosInvalidosException;
import com.projetos.omnilink.desafiotecnico.exceptions.RegistroDuplicadoException;
import com.projetos.omnilink.desafiotecnico.exceptions.UsuarioNaoEncontradoException;
import com.projetos.omnilink.desafiotecnico.repositories.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Description;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve salvar um novo usuário com sucesso")
    public void deveSalvarNovoUsuario() {
        UsuarioCreateDTO dto = getUsuarioCreateDTO();
        Usuario usuario = getUsuario();

        when(usuarioRepository.save(Mockito.any(Usuario.class))).thenAnswer(i -> i.getArguments()[0]);
        when(passwordEncoder.encode(Mockito.anyString())).thenReturn("senhaHash");

        usuarioService.criarUsuario(dto);

        verify(usuarioRepository, Mockito.times(1)).save(Mockito.any(Usuario.class));

        assertEquals("user teste", usuario.getNome());
        assertEquals("email@gmail.com", usuario.getEmail());
        assertEquals("00000000000", usuario.getCpf());
    }

    @Test
    @DisplayName("Deve atualizar informações do usuário")
    public void deveAtualizarUsuario() {
        Usuario usuario = getUsuario();
        UsuarioUpdateDTO dto = getUsuarioUpdateDto();

        when(usuarioRepository.findById(usuario.getId()))
                .thenReturn(Optional.of(usuario));

        when(usuarioRepository.save(Mockito.any(Usuario.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        usuarioService.editarUsuario(usuario.getId(), dto);

        verify(usuarioRepository, Mockito.times(1)).save(usuario);

        assertEquals(dto.getNome(), usuario.getNome());
        assertEquals(dto.getEmail(), usuario.getEmail());
        assertEquals("00000000000", usuario.getCpf());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar senha menor que 8 caracteres")
    public void deveFalharAoAtualizarSenhaCurta() {
        Usuario usuario = getUsuario();
        UsuarioUpdateSenhaDTO dto = new UsuarioUpdateSenhaDTO();
        dto.setSenha("1234");

        when(usuarioRepository.findById(usuario.getId()))
                .thenReturn(Optional.of(usuario));

        assertThrows(DadosInvalidosException.class, () -> {
            usuarioService.editarSenhaUsuario(usuario.getId(), dto);
        });

        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve atualizar senha do usuário")
    public void deveAtualizarSenhaUsuario() {
        Usuario usuario = getUsuario();
        UsuarioUpdateSenhaDTO dto = new UsuarioUpdateSenhaDTO();
        dto.setSenha("1231aaee");

        when(usuarioRepository.findById(usuario.getId()))
                .thenReturn(Optional.of(usuario));

        when(passwordEncoder.encode("1231aaee"))
                .thenReturn("senhaHash");

        when(usuarioRepository.save(Mockito.any(Usuario.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        usuarioService.editarSenhaUsuario(usuario.getId(), dto);

        verify(usuarioRepository, Mockito.times(1)).save(usuario);

        assertEquals("senhaHash", usuario.getSenha_hash());
    }

    @Test
    @Description("Deve retornar uma lista de usuários")
    public void deveRetornarListaUsuarios() {
        usuarioService.listarUsuarios();

        assertNotNull(usuarioRepository.findAll());
    }

    @Test
    @Description("Deve retornar uma lista de usuários associados a role informada")
    public void deveRetornarListaUsuariosPorRole() {
        Usuario usuarioMock = getUsuario();

        when(usuarioRepository.findUsuariosByRole(usuarioMock.getRole()))
                .thenReturn(List.of(usuarioMock));

        List<Usuario> usuarios = usuarioService.buscarUsuariosPorRole(usuarioMock.getRole().toString());

        assertNotNull(usuarios);
        assertFalse(usuarios.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar o usuário correspondente ao CPF informado para busca")
    public void deveRetornarUsuarioCorrespondenteAoCPF() {
        Usuario usuarioMock = getUsuario();

        when(usuarioRepository.findByCpf(usuarioMock.getCpf()))
                .thenReturn(Optional.of(usuarioMock));

        Usuario usuario = usuarioService.buscarUsuarioPorCpf(usuarioMock.getCpf());

        assertEquals(usuarioMock.getCpf(), usuario.getCpf());

        verify(usuarioRepository, Mockito.times(1)).findByCpf(usuarioMock.getCpf());
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF não for encontrado")
    public void deveLancarExcecaoQuandoCPFNaoEncontrado() {
        String cpf = "999999999";
        when(usuarioRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        Assertions.assertThrows(UsuarioNaoEncontradoException.class,
                () -> usuarioService.buscarUsuarioPorCpf(cpf));

        verify(usuarioRepository, Mockito.times(1)).findByCpf(cpf);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o CPF já possuir cadastro")
    public void deveRetornarExceptionCPFPossuiCadastro() {
        String cpf = "123456789";
        when(usuarioRepository.existsByCpf(cpf)).thenReturn(true);

        RegistroDuplicadoException exception = Assertions.assertThrows(
                RegistroDuplicadoException.class,
                () -> usuarioService.verificarDuplicadoPorCpf(cpf)
        );

        assertEquals("Já existe um usuário com esse CPF informado.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve excluir usuário correspondente ao ID informado")
    public void deveExcluirUsuario() {
        Usuario usuarioMock = getUsuario();

        when(usuarioRepository.findById(usuarioMock.getId())).thenReturn(Optional.of(usuarioMock));

        usuarioService.deleteUsuario(usuarioMock.getId());

        verify(usuarioRepository, Mockito.times(1)).findById(usuarioMock.getId());
        verify(usuarioRepository, Mockito.times(1)).delete(usuarioMock);
    }


    @Test
    @DisplayName("Deve lançar exceção ao tentar excluir usuário inexistente")
    public void deveLancarExcecaoAoExcluirUsuarioInexistente() {
        UUID idUsuario = UUID.randomUUID();

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.empty());

        Assertions.assertThrows(UsuarioNaoEncontradoException.class,
                () -> usuarioService.deleteUsuario(idUsuario));

        verify(usuarioRepository, Mockito.times(1)).findById(idUsuario);
        verify(usuarioRepository, Mockito.never()).delete(Mockito.any());
    }

    Usuario getUsuario() {
        UUID idUsuario = UUID.randomUUID();

        return Usuario.builder()
                .id(idUsuario)
                .nome("user teste")
                .email("email@gmail.com")
                .cpf("00000000000")
                .role(RoleEnum.FUNCIONARIO)
                .senha_hash("test@ndoUs3r")
                .build();
    }

    UsuarioCreateDTO getUsuarioCreateDTO() {
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();

        usuarioCreateDTO.setNome(getUsuario().getNome());
        usuarioCreateDTO.setEmail(getUsuario().getEmail());
        usuarioCreateDTO.setCpf(getUsuario().getCpf());
        usuarioCreateDTO.setSenha(getUsuario().getSenha_hash());
        usuarioCreateDTO.setRole(getUsuario().getRole());

        return usuarioCreateDTO;
    }

    UsuarioUpdateDTO getUsuarioUpdateDto() {
        UsuarioUpdateDTO usuarioUpdateDTO = new UsuarioUpdateDTO();

        usuarioUpdateDTO.setNome(getUsuario().getNome());
        usuarioUpdateDTO.setEmail(getUsuario().getEmail());
        usuarioUpdateDTO.setRole(getUsuario().getRole());

        return usuarioUpdateDTO;
    }
}