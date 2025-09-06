package com.projetos.omnilink.desafiotecnico.services;

import com.projetos.omnilink.desafiotecnico.entities.Usuario;
import com.projetos.omnilink.desafiotecnico.entities.dto.usuario.UsuarioCreateDTO;
import com.projetos.omnilink.desafiotecnico.entities.dto.usuario.UsuarioUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface UsuarioService {
    void criarUsuario(UsuarioCreateDTO usuarioCreateDTO);
    void editarUsuario(UUID id, UsuarioUpdateDTO usuarioUpdateDTO);
    void editarSenhaUsuario(UUID id, String senha);
    Usuario buscarUsuarioPorCpf(String cpf);
    List<Usuario> buscarUsuariosPorRole(String role);
    List<Usuario> listarUsuarios();
    void deleteUsuario(UUID idUsuario);
}
