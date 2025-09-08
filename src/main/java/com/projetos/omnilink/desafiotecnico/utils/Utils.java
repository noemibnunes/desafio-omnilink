package com.projetos.omnilink.desafiotecnico.utils;

import com.projetos.omnilink.desafiotecnico.exceptions.DadosInvalidosException;

public class Utils {
    private static final String SOMENTE_NUMEROS = "[^\\d]";

    public static void verificarCamposObrigatorios(String nome, String email, String cpf) {
        if (nome == null || nome.isBlank()) {
            throw new DadosInvalidosException("O nome é obrigatório.");
        }

        if (email == null || email.isBlank()) {
            throw new DadosInvalidosException("O email é obrigatório.");
        }

        if (cpf == null || cpf.isBlank()) {
            throw new DadosInvalidosException("O CPF é obrigatório.");
        }
    }

    public static String normalizarCpf(String cpf) {
        return cpf.replaceAll(SOMENTE_NUMEROS, "");
    }

}
