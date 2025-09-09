package com.projetos.omnilink.desafiotecnico.services.impl;

import com.projetos.omnilink.desafiotecnico.entities.Usuario;
import com.projetos.omnilink.desafiotecnico.entities.dto.usuario.UsuarioCreateDTO;
import com.projetos.omnilink.desafiotecnico.entities.dto.usuario.UsuarioUpdateDTO;
import com.projetos.omnilink.desafiotecnico.entities.dto.usuario.UsuarioUpdateSenhaDTO;
import com.projetos.omnilink.desafiotecnico.enums.RoleEnum;
import com.projetos.omnilink.desafiotecnico.exceptions.RegistroDuplicadoException;
import com.projetos.omnilink.desafiotecnico.exceptions.UsuarioNaoEncontradoException;
import com.projetos.omnilink.desafiotecnico.repositories.UsuarioRepository;
import com.projetos.omnilink.desafiotecnico.services.UsuarioService;
import com.projetos.omnilink.desafiotecnico.utils.UsuarioValidator;
import com.projetos.omnilink.desafiotecnico.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.projetos.omnilink.desafiotecnico.mappers.UsuarioMapper.toEntity;
import static com.projetos.omnilink.desafiotecnico.mappers.UsuarioMapper.updateUsuarioFromDto;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void criarUsuario(UsuarioCreateDTO usuarioCreateDTO) {
        Usuario usuario = toEntity(usuarioCreateDTO);

        usuario.setCpf(Utils.normalizarCpf(usuario.getCpf()));

        String senhaSemEnconder = usuarioCreateDTO.getSenha();
        UsuarioValidator.verificarDadosUsuario(usuario, senhaSemEnconder);

        verificarDuplicadoPorCpf(usuario.getCpf());
        UsuarioValidator.verificarDadosUsuario(usuario, senhaSemEnconder);

        usuario.setSenha_hash(passwordEncoder.encode(usuarioCreateDTO.getSenha()));
        usuarioRepository.save(usuario);
    }

    @Override
    public void editarUsuario(UUID id, UsuarioUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado."));

        updateUsuarioFromDto(usuario, dto);
        UsuarioValidator.verificarDadosUsuario(usuario);
        usuarioRepository.save(usuario);
    }

    @Override
    public void editarSenhaUsuario(UUID id, UsuarioUpdateSenhaDTO dto) {
        UsuarioValidator.verificarSenha(dto.getSenha());

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado."));

        usuario.setSenha_hash(passwordEncoder.encode(dto.getSenha()));
        usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarUsuarioPorCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado."));
    }

    @Override
    public List<Usuario> buscarUsuariosPorRole(String role) {
        RoleEnum roleEnum = RoleEnum.from(role);
        List<Usuario> usuarios = usuarioRepository.findUsuariosByRole(roleEnum);
        if (usuarios.isEmpty()) {
            throw new UsuarioNaoEncontradoException("Usuários não encontrados.");
        }
        return usuarios;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public void deleteUsuario(UUID idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado."));

        usuarioRepository.delete(usuario);
    }

    public void verificarDuplicadoPorCpf(String cpf) {
        boolean existe = usuarioRepository.existsByCpf(Utils.normalizarCpf(cpf));

        if (existe) {
            throw new RegistroDuplicadoException("Já existe um usuário com esse CPF informado.");
        }
    }
}
