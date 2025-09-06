package com.projetos.omnilink.desafiotecnico.utils;

import com.projetos.omnilink.desafiotecnico.entities.Cliente;
import com.projetos.omnilink.desafiotecnico.entities.Usuario;
import com.projetos.omnilink.desafiotecnico.exceptions.DadosInvalidosException;

import java.time.LocalDate;
import java.time.Period;

public class UsuarioValidator {
    public static void verificarDadosUsuario(Usuario usuario) {
        Utils.verificarCamposObrigatorios(usuario.getNome(), usuario.getEmail(), usuario.getCpf());

        if (usuario.getRole() == null) {
            throw new DadosInvalidosException("A role do usuário é obrigatória.");
        }
    }
}
