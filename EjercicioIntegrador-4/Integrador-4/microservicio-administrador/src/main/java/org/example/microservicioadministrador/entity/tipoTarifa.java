package org.example.microservicioadministrador.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum tipoTarifa {

        NORMAL,
        PAUSA,
        PROMOCIONAL,
        FIN_SEMANA;

    @JsonCreator
    public static tipoTarifa fromString(String value) {
        if (value == null) {
            return null;
        }
        try {
            return tipoTarifa.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de tarifa inválido: " + value);
        }
    }
}
