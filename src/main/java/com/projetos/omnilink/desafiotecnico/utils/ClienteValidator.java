package com.projetos.omnilink.desafiotecnico.utils;

import com.projetos.omnilink.desafiotecnico.entities.Cliente;
import com.projetos.omnilink.desafiotecnico.exception.DadosInvalidosException;

import java.time.LocalDate;
import java.time.Period;

public class ClienteValidator {

    private static final String REGEX_CPF = "\\d{11}";
    private static final String REGEX_EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    public static void verificarDadosCliente(Cliente cliente) {
        if (cliente.getCpf() == null || !cliente.getCpf().matches(REGEX_CPF)) {
            throw new DadosInvalidosException("CPF inválido.");
        }

        if (cliente.getEmail() == null || !cliente.getEmail().matches(REGEX_EMAIL)) {
            throw new DadosInvalidosException("E-mail inválido.");
        }

        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            throw new DadosInvalidosException("Nome não pode estar vazio.");
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
}
