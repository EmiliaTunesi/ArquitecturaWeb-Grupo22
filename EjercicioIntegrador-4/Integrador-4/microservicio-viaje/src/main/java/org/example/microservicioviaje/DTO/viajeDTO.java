package org.example.microservicioviaje.DTO;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public class viajeDTO {

    private Long id;
    private int idCuentaUsuario;
    private int idMonopatin;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private int paradaInicioId;
    private int paradaFinId;
    private BigDecimal kmRecorridos;
    private int tarifaId;

    public viajeDTO() {}

    public viajeDTO(int idCuentaUsuario, int idMonopatin, LocalDateTime fechaInicio, int paradaInicioId, int tarifaId) {
        this.idCuentaUsuario = idCuentaUsuario;
        this.idMonopatin = idMonopatin;
        this.fechaInicio = fechaInicio;
        this.paradaInicioId = paradaInicioId;
        this.tarifaId = tarifaId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIdCuentaUsuario() {
        return idCuentaUsuario;
    }

    public void setIdCuentaUsuario(int idCuentaUsuario) {
        this.idCuentaUsuario = idCuentaUsuario;
    }

    public int getIdMonopatin() {
        return idMonopatin;
    }

    public void setIdMonopatin(int idMonopatin) {
        this.idMonopatin = idMonopatin;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getParadaInicioId() {
        return paradaInicioId;
    }

    public void setParadaInicioId(int paradaInicioId) {
        this.paradaInicioId = paradaInicioId;
    }

    public int getParadaFinId() {
        return paradaFinId;
    }

    public void setParadaFinId(int paradaFinId) {
        this.paradaFinId = paradaFinId;
    }

    public BigDecimal getKmRecorridos() {
        return kmRecorridos;
    }

    public void setKmRecorridos(BigDecimal kmRecorridos) {
        this.kmRecorridos = kmRecorridos;
    }

    public int getTarifaId() {
        return tarifaId;
    }

    public void setTarifaId(int tarifaId) {
        this.tarifaId = tarifaId;
    }
}
