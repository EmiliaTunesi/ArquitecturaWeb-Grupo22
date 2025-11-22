package com.trabajointegrador.microserviciousuario.feing;

import com.trabajointegrador.microserviciousuario.dto.DatosVinculacionDTO;
import com.trabajointegrador.microserviciousuario.dto.RespuestaDTO;
import java.math.BigDecimal;

public interface MercadoPagoClient {

        String vincularCuenta(DatosVinculacionDTO datos);
        RespuestaDTO realizarCobro(String idMercadoPago, BigDecimal monto);
}
