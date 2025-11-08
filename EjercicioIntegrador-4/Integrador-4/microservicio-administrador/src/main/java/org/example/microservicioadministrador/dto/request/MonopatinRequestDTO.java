package org.example.microservicioadministrador.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonopatinRequestDTO {
    // ⚙️ id no se manda desde el cliente (lo genera la BD)
    private Long id;

    // Estado puede ser un enum (por ejemplo: DISPONIBLE, EN_USO, MANTENIMIENTO)
    @NotNull(message = "El estado del monopatín es obligatorio")
    private String estado;

    // 📍 Coordenadas actuales
    @NotNull(message = "La latitud actual es obligatoria")
    private Double latitudActual;

    @NotNull(message = "La longitud actual es obligatoria")
    private Double longitudActual;

    // 📏 Kilómetros recorridos hasta ahora
    @Positive(message = "Los kilómetros deben ser un valor positivo")
    private Double kilometros;

    // ⏱️ Tiempo total de uso en minutos
    @Positive(message = "El tiempo de uso debe ser un valor positivo")
    private Integer tiempoUso;

    // ⏸️ Tiempo total de pausas en minutos
    @Positive(message = "El tiempo de pausa debe ser un valor positivo")
    private Integer tiempoPausa;

    // 🗓️ Fecha de alta del monopatín (puede autogenerarse en el backend)
    private LocalDateTime fechaAlta;
}
