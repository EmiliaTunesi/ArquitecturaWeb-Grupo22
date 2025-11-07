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
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + usuarioId));

        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada: " + cuentaId));

        UsuarioCuenta usuarioCuenta = new UsuarioCuenta(usuario, cuenta);
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
    public void anularCuenta(Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada: " + cuentaId));

        cuenta.setActiva(false);
        cuenta.setFechaBaja(LocalDate.now());
        cuentaRepository.save(cuenta);

        // Las vinculaciones NO se eliminan, quedan históricas.
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> obtenerUsuariosPorCuenta(Long cuentaId) {
        List<UsuarioCuenta> vinculaciones = usuarioCuentaRepository.findByCuentaId(cuentaId);
        return vinculaciones.stream()
                .map(v -> UsuarioMapper.toDTO(v.getUsuario()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean existeVinculacion(Long usuarioId, Long cuentaId) {
        UsuarioCuentaid id = new UsuarioCuentaid(usuarioId, cuentaId);
        return usuarioCuentaRepository.existsById(id);
    }
}