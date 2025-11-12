package org.example.microserviciomonopatin.dto.dtoRequest;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IniciarMantenimientoRequestDTO {

    private Long monopatinId;
    private String descripcion;
    private String tipoMantenimiento;
}
