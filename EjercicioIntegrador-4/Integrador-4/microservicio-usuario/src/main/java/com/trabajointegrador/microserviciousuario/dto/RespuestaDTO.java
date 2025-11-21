package com.trabajointegrador.microserviciousuario.dto;

public class RespuestaDTO {
    private String idTransaccion;
    private String estado;

    public RespuestaDTO(String idTransaccion, String estado) {
        this.idTransaccion = idTransaccion;
        this.estado = estado;
    }
    public String getEstado() { return estado; }
}