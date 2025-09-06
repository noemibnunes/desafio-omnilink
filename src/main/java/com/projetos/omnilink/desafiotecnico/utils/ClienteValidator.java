package com.projetos.omnilink.desafiotecnico.utils;

import com.projetos.omnilink.desafiotecnico.entities.Cliente;
import com.projetos.omnilink.desafiotecnico.exceptions.DadosInvalidosException;

import java.time.LocalDate;
import java.time.Period;

public class ClienteValidator {
    private static final String SOMENTE_NUMEROS = "[^\\d]";

    public static void verificarDadosCliente(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            throw new DadosInvalidosException("O nome é obrigatório.");
        }

        if (cliente.getEmail() == null || cliente.getEmail().isBlank()) {
            throw new DadosInvalidosException("O email é obrigatório.");
        }

        if (cliente.getCpf() == null || cliente.getCpf().isBlank()) {
            throw new DadosInvalidosException("O CPF é obrigatório.");
        }

        if (cliente.getDataNascimento() != null) {
            LocalDate hoje = LocalDate.now();

            if (cliente.getDataNascimento().isAfter(hoje)) {
                throw new DadosInvalidosException("Data de nascimento não pode ser no futuro.");
            }

            int idade = Period.between(cliente.getDataNascimento(), hoje).getYears();

            if (idade < 18) {
                throw new DadosInvalidosException("O cliente deve ter pelo menos 18 anos.");
            }
        }
    }


    public static String normalizarCpf(String cpf) {
        return cpf.replaceAll(SOMENTE_NUMEROS, "");
    }

}
