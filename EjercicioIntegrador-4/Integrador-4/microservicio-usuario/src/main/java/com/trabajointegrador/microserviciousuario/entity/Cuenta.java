package com.trabajointegrador.microserviciousuario.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cuentas")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroIdentificador;

    @Column(nullable = false)
    private LocalDate fechaAlta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCuenta tipoCuenta; // BÁSICA o PREMIUM

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UsuarioCuenta> usuarios = new HashSet<>();

    public Cuenta() {}

    public Cuenta(String numeroIdentificador, LocalDate fechaAlta, TipoCuenta tipoCuenta) {
        this.numeroIdentificador = numeroIdentificador;
        this.fechaAlta = fechaAlta;
        this.tipoCuenta = tipoCuenta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroIdentificador() {
        return numeroIdentificador;
    }

    public void setNumeroIdentificador(String numeroIdentificador) {
        this.numeroIdentificador = numeroIdentificador;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Set<UsuarioCuenta> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<UsuarioCuenta> usuarios) {
        this.usuarios = usuarios;
    }

    public enum TipoCuenta {
        BASICA,
        PREMIUM
    }
}