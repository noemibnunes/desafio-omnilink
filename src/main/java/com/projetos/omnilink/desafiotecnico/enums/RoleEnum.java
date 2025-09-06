package com.projetos.omnilink.desafiotecnico.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RoleEnum {
    ROLE_ADMIN,
    ROLE_USUARIO,
    ROLE_CLIENTE;

    @JsonCreator
    public static RoleEnum from(String value) {
        return RoleEnum.valueOf(value.toUpperCase());
    }
}