package com.projetos.omnilink.desafiotecnico.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RoleEnum {
    ADMIN,
    CLIENTE,
    FUNCIONARIO;

    @JsonCreator
    public static RoleEnum from(String value) {
        return RoleEnum.valueOf(value.toUpperCase());
    }
}