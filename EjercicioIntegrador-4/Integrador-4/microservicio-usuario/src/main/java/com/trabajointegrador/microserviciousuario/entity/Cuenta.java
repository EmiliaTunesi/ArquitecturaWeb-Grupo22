package com.trabajointegrador.microserviciousuario.entity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cuentas")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_cuenta", nullable = false, unique = true)
    private String numeroCuenta;

    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta", nullable = false)
    private TipoCuenta tipoCuenta;

    @Column(name = "saldo_creditos", nullable = false)
    private BigDecimal saldoCreditos = BigDecimal.ZERO;

    @Column(name = "cuenta_mercadopago_id")
    private String cuentaMercadoPagoId;

    @Column(nullable = false)
    private boolean activa = true;

    @Column(name = "km_recorridos_mes")
    private BigDecimal kmRecorridosMes;

    @Column(name = "fecha_renovacion_cupo")
    private LocalDate fechaRenovacionCupo;

    @Column(name = "fecha_baja")
    private LocalDate fechaBaja;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UsuarioCuenta> usuarios = new HashSet<>();


    public Cuenta() {}

    public Cuenta(String numeroCuenta, LocalDate fechaAlta, TipoCuenta tipoCuenta) {
        this.numeroCuenta = numeroCuenta;
        this.fechaAlta = fechaAlta;
        this.tipoCuenta = tipoCuenta;
        this.activa = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
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

    public BigDecimal getSaldoCreditos() {
        return saldoCreditos;
    }

    public void setSaldoCreditos(BigDecimal saldoCreditos) {
        this.saldoCreditos = saldoCreditos;
    }

    public String getCuentaMercadoPagoId() {
        return cuentaMercadoPagoId;
    }

    public void setCuentaMercadoPagoId(String cuentaMercadoPagoId) {
        this.cuentaMercadoPagoId = cuentaMercadoPagoId;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
        this.fechaBaja = activa ? null : LocalDate.now();
    }

    public BigDecimal getKmRecorridosMes() {
        return kmRecorridosMes;
    }

    public void setKmRecorridosMes(BigDecimal kmRecorridosMes) {
        this.kmRecorridosMes = kmRecorridosMes;
    }

    public LocalDate getFechaRenovacionCupo() {
        return fechaRenovacionCupo;
    }

    public void setFechaRenovacionCupo(LocalDate fechaRenovacionCupo) {
        this.fechaRenovacionCupo = fechaRenovacionCupo;
    }

    public LocalDate getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
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
