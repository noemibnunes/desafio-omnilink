package com.projetos.omnilink.desafiotecnico.utils;

import com.projetos.omnilink.desafiotecnico.entities.Veiculo;
import com.projetos.omnilink.desafiotecnico.exceptions.DadosInvalidosException;

import java.time.LocalDate;

public class VeiculoValidator {

    public final static String REGEX_CHASSI = "^[A-HJ-NPR-Z0-9]{17}$";

    public static void verificarDadosVeiculo(Veiculo veiculo) {
        if (veiculo.getChassi() == null || veiculo.getChassi().isBlank()) {
            throw new DadosInvalidosException("Número de Chassi é obrigatório.");
        }

        if (!veiculo.getChassi().matches(REGEX_CHASSI)) {
            throw new DadosInvalidosException("Número de Chassi inválido.");
        }

        if (veiculo.getMarca() == null || veiculo.getMarca().isBlank()) {
            throw new DadosInvalidosException("A marca do veículo é obrigatória.");
        }

        if (veiculo.getModelo() == null || veiculo.getModelo().isBlank()) {
            throw new DadosInvalidosException("O modelo do veículo é obrigatório.");
        }

        if (veiculo.getAno() == 0) {
            throw new DadosInvalidosException("Ano do veículo é obrigatório.");
        }

        int anoAtual = LocalDate.now().getYear();
        int anoMaisAntigo = 1950;
        int anoLimite = anoAtual + 1;

        if (veiculo.getAno() < anoMaisAntigo || veiculo.getAno() > anoLimite) {
            throw new DadosInvalidosException("Ano do carro inválido.");
        }

        if (veiculo.getTipoCombustivel() == null) {
            throw new DadosInvalidosException("Tipo de combustível é obrigatório.");
        }

        if (veiculo.getTipoVeiculo() == null) {
            throw new DadosInvalidosException("Tipo de veículo é obrigatório.");
        }
    }
}
