package org.example.microservicioviaje.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Cuenta {

    private Long id;
    private BigDecimal saldo;
    // CAMPOS??
}