package com.trabajointegrador.microserviciousuario.service;

import com.trabajointegrador.microserviciousuario.dto.UsuarioCuentaDTO;
import com.trabajointegrador.microserviciousuario.dto.UsuarioDTO;
import com.trabajointegrador.microserviciousuario.entity.Cuenta;
import com.trabajointegrador.microserviciousuario.entity.Usuario;
import com.trabajointegrador.microserviciousuario.entity.UsuarioCuenta;
import com.trabajointegrador.microserviciousuario.entity.UsuarioCuentaid;
import com.trabajointegrador.microserviciousuario.mappers.UsuarioCuentaMapper;
import com.trabajointegrador.microserviciousuario.mappers.UsuarioMapper;
import com.trabajointegrador.microserviciousuario.repository.CuentaRepository;
import com.trabajointegrador.microserviciousuario.repository.UsuarioCuentaRepository;
import com.trabajointegrador.microserviciousuario.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CuentaUsuarioService {

    private final UsuarioCuentaRepository usuarioCuentaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CuentaRepository cuentaRepository;

    public CuentaUsuarioService(
            UsuarioCuentaRepository usuarioCuentaRepository,
            UsuarioRepository usuarioRepository,
            CuentaRepository cuentaRepository
    ) {
        this.usuarioCuentaRepository = usuarioCuentaRepository;
        this.usuarioRepository = usuarioRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Transactional
    public UsuarioCuentaDTO vincularUsuarioCuenta(Long usuarioId, Long cuentaId) {
        UsuarioCuentaid id = new UsuarioCuentaid(usuarioId, cuentaId);

        if (usuarioCuentaRepository.existsById(id)) {
            throw new RuntimeException("El usuario ya está vinculado a esta cuenta.");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + cuentaId));

        UsuarioCuenta usuarioCuenta = new UsuarioCuenta(usuario, cuenta);
        usuarioCuenta.setActiva(true);
        usuarioCuenta.setFechaVinculacion(LocalDate.now());

        UsuarioCuenta guardada = usuarioCuentaRepository.save(usuarioCuenta);
        return UsuarioCuentaMapper.toDTO(guardada);
    }


    @Transactional(readOnly = true)
    public List<UsuarioCuentaDTO> listarVinculaciones() {
        return usuarioCuentaRepository.findAll()
                .stream()
                .map(UsuarioCuentaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void desactivarVinculacion(Long usuarioId, Long cuentaId) {
        UsuarioCuentaid id = new UsuarioCuentaid(usuarioId, cuentaId);
        UsuarioCuenta uc = usuarioCuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vinculación no encontrada."));
        uc.setActiva(false);
        usuarioCuentaRepository.save(uc);
    }

    @Transactional
    public void anularCuenta(Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + cuentaId));

        cuenta.setActiva(false);
        cuenta.setFechaBaja(LocalDate.now());
        cuentaRepository.save(cuenta);

        List<UsuarioCuenta> vinculaciones = usuarioCuentaRepository.findByCuentaId(cuentaId);
        vinculaciones.forEach(uc -> uc.setActiva(false));
        usuarioCuentaRepository.saveAll(vinculaciones);
    }

    // Obtener todos los usuarios activos asociados a una cuenta
    @Transactional(readOnly = true)
    public List<UsuarioDTO> obtenerUsuariosActivosPorCuenta(Long cuentaId) {
        List<UsuarioCuenta> vinculaciones = usuarioCuentaRepository.findByCuentaIdAndActivaTrue(cuentaId);
        return vinculaciones.stream()
                .map(v -> UsuarioMapper.toDTO(v.getUsuario()))
                .collect(Collectors.toList());
    }

    // Consultar uso de monopatines por usuario o cuenta (interfaz hacia otro microservicio)
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerUsoPorCuenta(Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + cuentaId));


        Map<String, Object> reporte = new HashMap<>();
        reporte.put("cuentaId", cuentaId);
        reporte.put("usuariosAsociados", obtenerUsuariosActivosPorCuenta(cuentaId));
        reporte.put("mensaje", "Datos listos para cálculo de uso (integración con microservicio de viajes)");
        return reporte;
    }

    @Transactional
    public void reactivarVinculacion(Long usuarioId, Long cuentaId) {
        UsuarioCuentaid id = new UsuarioCuentaid(usuarioId, cuentaId);
        UsuarioCuenta uc = usuarioCuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vinculación no encontrada."));
        uc.setActiva(true);
        usuarioCuentaRepository.save(uc);
    }

    @Transactional(readOnly = true)
    public List<UsuarioCuentaDTO> listarVinculacionesActivas() {
        return usuarioCuentaRepository.findByActivaTrue()
                .stream()
                .map(UsuarioCuentaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UsuarioCuentaDTO> listarVinculacionesInactivas() {
        return usuarioCuentaRepository.findByActivaFalse()
                .stream()
                .map(UsuarioCuentaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean existeVinculacion(Long usuarioId, Long cuentaId) {
        UsuarioCuentaid id = new UsuarioCuentaid(usuarioId, cuentaId);
        return usuarioCuentaRepository.existsById(id);
    }
}
