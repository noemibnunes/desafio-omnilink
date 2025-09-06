package com.projetos.omnilink.desafiotecnico.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TipoCombustivelEnum {
    GASOLINA,
    ALCOOL,
    DIESEL,
    ELETRICO,
    FLEX;

    @JsonCreator
    public static TipoCombustivelEnum from(String value) {
        return TipoCombustivelEnum.valueOf(value.toUpperCase());
    }

}

