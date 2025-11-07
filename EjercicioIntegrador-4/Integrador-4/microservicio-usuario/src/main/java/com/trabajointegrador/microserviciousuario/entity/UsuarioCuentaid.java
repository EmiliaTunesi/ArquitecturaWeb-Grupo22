package com.trabajointegrador.microserviciousuario.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UsuarioCuentaid implements Serializable {

    private Long usuarioId;
    private Long cuentaId;

    public UsuarioCuentaid() {}

    public UsuarioCuentaid(Long usuarioId, Long cuentaId) {
        this.usuarioId = usuarioId;
        this.cuentaId = cuentaId;
    }

    // getters y setters
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getCuentaId() { return cuentaId; }
    public void setCuentaId(Long cuentaId) { this.cuentaId = cuentaId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioCuentaid)) return false;
        UsuarioCuentaid that = (UsuarioCuentaid) o;
        return Objects.equals(usuarioId, that.usuarioId) && Objects.equals(cuentaId, that.cuentaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, cuentaId);
    }
}
