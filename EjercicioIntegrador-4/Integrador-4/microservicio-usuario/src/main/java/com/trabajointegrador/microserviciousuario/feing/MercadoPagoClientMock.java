package com.trabajointegrador.microserviciousuario.feing;


import com.trabajointegrador.microserviciousuario.dto.DatosVinculacionDTO;
import com.trabajointegrador.microserviciousuario.dto.RespuestaDTO;
import com.trabajointegrador.microserviciousuario.feing.MercadoPagoClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class MercadoPagoClientMock implements MercadoPagoClient {

   @Override
    public String vincularCuenta(DatosVinculacionDTO datos) {
       if (datos == null || datos.getEmail() == null) {
           throw new IllegalArgumentException("Datos de vinculación inválidos");
       }
       // Exito estandar
       System.out.println("MOCK MP: Vinculación exitosa.");
       return "MP-" + UUID.randomUUID().toString();
    }

    @Override
    public RespuestaDTO realizarCobro(String idMercadoPago, BigDecimal monto) {

        // Validación genérica: No se puede cobrar negativo o cero
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
        // Validación genérica: ID requerido
        if (idMercadoPago == null || idMercadoPago.isEmpty()) {
            throw new IllegalArgumentException("ID de cuenta inválido");
        }
       System.out.println("--> MOCK: Cobrando $" + monto + " a la cuenta " + idMercadoPago);

        // Genera un ID de transacción único
        String idTransaccion = "TX-" + UUID.randomUUID().toString();

        return new RespuestaDTO(idTransaccion, "APROBADO");
    }
}
