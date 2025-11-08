package org.example.microservicioadministrador.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MantenimientoRequestDTO {

    //private Long id;
    @NotNull(message = "El ID del monopatín es obligatorio")
    private Long monopatin_id;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fecha_inicio;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fecha_fin; // Puede ser null

    @NotNull(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "El ID del encargado es obligatorio")
    private Long encargado_id;

    @NotNull(message = "El tipo de mantenimiento es obligatorio")
    private String tipo_mantenimiento;
    // PREVENTIVO, CORRECTIVO, EMERGENCIA
    //enum o check?
}
