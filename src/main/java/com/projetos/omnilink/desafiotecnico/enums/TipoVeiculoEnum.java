package com.projetos.omnilink.desafiotecnico.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TipoVeiculoEnum {
    CARRO,
    MOTO,
    CAMINHAO,
    ONIBUS,
    VAN;

    @JsonCreator
    public static TipoVeiculoEnum from(String value) {
        return TipoVeiculoEnum.valueOf(value.toUpperCase());
    }
}

