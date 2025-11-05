package com.trabajointegrador.microserviciousuario.entity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "usuario_cuenta")
public class UsuarioCuenta {

    @EmbeddedId
    private UsuarioCuentaid id;

    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @MapsId("cuentaId")
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;

    @Column(nullable = false)
    private boolean activa = true;

    @Column(nullable = false)
    private LocalDate fechaVinculacion = LocalDate.now();

    public UsuarioCuenta() {}

    public UsuarioCuenta(Usuario usuario, Cuenta cuenta) {
        this.usuario = usuario;
        this.cuenta = cuenta;
        this.id = new UsuarioCuentaid(usuario.getId(), cuenta.getId());
    }

    public UsuarioCuentaid getId() { return id; }
    public void setId(UsuarioCuentaid id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Cuenta getCuenta() { return cuenta; }
    public void setCuenta(Cuenta cuenta) { this.cuenta = cuenta; }

    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }

    public LocalDate getFechaVinculacion() { return fechaVinculacion; }
    public void setFechaVinculacion(LocalDate fechaVinculacion) { this.fechaVinculacion = fechaVinculacion; }
}
