package com.trabajointegrador.microserviciousuario.dto;
import java.math.BigDecimal;

// DTO para enviar datos
public class DatosVinculacionDTO {

        private String email;
        public DatosVinculacionDTO() {}
        public DatosVinculacionDTO(String email) {
            this.email = email;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
}

