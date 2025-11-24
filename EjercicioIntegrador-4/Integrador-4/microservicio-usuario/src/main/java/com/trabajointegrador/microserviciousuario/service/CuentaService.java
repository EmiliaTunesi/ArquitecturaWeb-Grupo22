package com.trabajointegrador.microserviciousuario.service;

import com.trabajointegrador.microserviciousuario.dto.CuentaDTO;
import com.trabajointegrador.microserviciousuario.entity.Cuenta;
import com.trabajointegrador.microserviciousuario.entity.Usuario;
import com.trabajointegrador.microserviciousuario.mappers.CuentaMapper;
import com.trabajointegrador.microserviciousuario.repository.CuentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Transactional
    public CuentaDTO crearCuenta(CuentaDTO dto) {
        Cuenta cuenta = CuentaMapper.toEntity(dto);
        cuenta.setActiva(true);
        cuenta.setFechaAlta(LocalDate.now());
        Cuenta guardada = cuentaRepository.save(cuenta);
        return CuentaMapper.toDTO(guardada);
    }

    @Transactional(readOnly = true)
    public List<CuentaDTO> listarCuentas() {
        return cuentaRepository.findAll()
                .stream()
                .map(CuentaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CuentaDTO obtenerPorId(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));
        return CuentaMapper.toDTO(cuenta);
    }

    @Transactional
    public CuentaDTO actualizarCuenta(Long id, CuentaDTO dto) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));

        cuenta.setNumeroCuenta(dto.getNumeroIdentificador());
        cuenta.setFechaAlta(dto.getFechaAlta());
        cuenta.setTipoCuenta(Cuenta.TipoCuenta.valueOf(dto.getTipoCuenta().toUpperCase()));
        cuenta.setKmRecorridosMes(dto.getKmRecorridosMes());
        cuenta.setSaldoCreditos(dto.getSaldoCreditos());
        cuenta.setFechaRenovacionCupo(dto.getFechaRenovacionCupo());
        cuenta.setCuentaMercadoPagoId(dto.getCuentaMercadoPagoId());

        Cuenta actualizada = cuentaRepository.save(cuenta);
        return CuentaMapper.toDTO(actualizada);
    }

    @Transactional
    public void eliminarCuenta(Long id) {
        if (!cuentaRepository.existsById(id)) {
            throw new RuntimeException("Cuenta no encontrada con ID: " + id);
        }
        cuentaRepository.deleteById(id);
    }

    @Transactional
    public CuentaDTO anularCuenta(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));

        if (!cuenta.isActiva()) {
            throw new RuntimeException("La cuenta ya está inactiva.");
        }

        cuenta.setActiva(false);
        Cuenta actualizada = cuentaRepository.save(cuenta);

        return CuentaMapper.toDTO(actualizada);
    }

    @Transactional
    public CuentaDTO reactivarCuenta(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));

        if (cuenta.isActiva()) {
            throw new RuntimeException("La cuenta ya está activa.");
        }

        cuenta.setActiva(true);
        cuenta.setFechaBaja(null);

        Cuenta actualizada = cuentaRepository.save(cuenta);
        return CuentaMapper.toDTO(actualizada);
    }

    @Transactional(readOnly = true)
    public List<CuentaDTO> listarCuentasActivas() {
        return cuentaRepository.findAll()
                .stream()
                .filter(Cuenta::isActiva)
                .map(CuentaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CuentaDTO> listarCuentasInactivas() {
        return cuentaRepository.findAll()
                .stream()
                .filter(c -> !c.isActiva())
                .map(CuentaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean isCuentaDisponible(Long idCuenta) {
        return cuentaRepository.findById(idCuenta)
                .map(Cuenta::isActiva)
                .orElse(false);
    }


    public boolean esCuentaPremium(Long idCuenta) {
        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        return cuenta.getTipoCuenta() == Cuenta.TipoCuenta.PREMIUM;
    }
}
