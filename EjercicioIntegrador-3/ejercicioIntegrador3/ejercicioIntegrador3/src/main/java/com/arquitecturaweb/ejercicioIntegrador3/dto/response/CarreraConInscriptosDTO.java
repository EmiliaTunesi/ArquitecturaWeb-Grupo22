package com.arquitecturaweb.ejercicioIntegrador3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CarreraConInscriptosDTO {
    private long carreraId;
    private String nombreCarrera;
    private long cantidadInscriptos;

}

