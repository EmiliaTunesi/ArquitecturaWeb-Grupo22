package org.example.microservicioadministrador.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParadaRequestDTO {
    // ⚙️ id no se manda desde el cliente (lo genera la BD)
    private Long id;

    // Estado puede ser un enum (por ejemplo: DISPONIBLE, EN_USO, MANTENIMIENTO)
    @NotNull(message = "El nombre de la parada es obligatorio")
    private String nombre;

    // 📍 Coordenadas actuales
    @NotNull(message = "La latitud actual es obligatoria")
    private Double latitudActual;

    @NotNull(message = "La longitud actual es obligatoria")
    private Double longitudActual;

    @NotNull(message = "La capacidad es obligatoria")
    private Integer capacidad;

    @NotNull (message = "activa es obli")
    private Boolean activa;

    @NotNull (message = "la fecha es obligatoria")
    private Date fecha;

    private List<MonopatinRequestDTO> monopatines;
}
