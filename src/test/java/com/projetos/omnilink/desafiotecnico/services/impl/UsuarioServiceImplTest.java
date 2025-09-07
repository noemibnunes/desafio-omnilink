package com.projetos.omnilink.desafiotecnico.services.impl;

import com.projetos.omnilink.desafiotecnico.entities.Cliente;
import com.projetos.omnilink.desafiotecnico.entities.Usuario;
import com.projetos.omnilink.desafiotecnico.entities.dto.usuario.UsuarioCreateDTO;
import com.projetos.omnilink.desafiotecnico.entities.dto.usuario.UsuarioUpdateDTO;
import com.projetos.omnilink.desafiotecnico.enums.RoleEnum;
import com.projetos.omnilink.desafiotecnico.exceptions.RegistroDuplicadoException;
import com.projetos.omnilink.desafiotecnico.exceptions.UsuarioNaoEncontradoException;
import com.projetos.omnilink.desafiotecnico.mappers.UsuarioMapper;
import com.projetos.omnilink.desafiotecnico.repositories.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UsuarioServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
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

        Assertions.assertEquals(usuarioMock.getCpf(), usuario.getCpf());

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

        Assertions.assertEquals("Já existe um usuário com esse CPF informado.", exception.getMessage());
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
                .senha_hash(passwordEncoder.encode("test3"))
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