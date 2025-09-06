package com.projetos.omnilink.desafiotecnico.utils;

import com.projetos.omnilink.desafiotecnico.entities.Cliente;
import com.projetos.omnilink.desafiotecnico.exceptions.DadosInvalidosException;

import java.time.LocalDate;
import java.time.Period;

public class ClienteValidator {
    public static void verificarDadosCliente(Cliente cliente) {
        Utils.verificarCamposObrigatorios(cliente.getNome(), cliente.getEmail(), cliente.getCpf());

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
