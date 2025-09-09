package com.projetos.omnilink.desafiotecnico.utils;

import com.projetos.omnilink.desafiotecnico.entities.Usuario;
import com.projetos.omnilink.desafiotecnico.exceptions.DadosInvalidosException;

public class UsuarioValidator {
    public static void verificarDadosUsuario(Usuario usuario, String senhaSemEnconder) {
        verificarDadosUsuario(usuario);

        verificarSenha(senhaSemEnconder);
    }

    public static void verificarDadosUsuario(Usuario usuario) {
        Utils.verificarCamposObrigatorios(usuario.getNome(), usuario.getEmail(), usuario.getCpf());

        if (usuario.getRole() == null) {
            throw new DadosInvalidosException("A role do usuário é obrigatória.");
        }
    }

    public static void verificarSenha(String senha) {
        if (senha == null || senha.length() < 8) {
            throw new DadosInvalidosException("A senha precisa conter pelo menos 8 caracteres.");
        }
    }
}
