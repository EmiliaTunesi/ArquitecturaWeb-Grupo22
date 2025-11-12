package com.trabajointegrador.microserviciousuario.service;

import com.trabajointegrador.microserviciousuario.dto.*;
import com.trabajointegrador.microserviciousuario.entity.Usuario;
import com.trabajointegrador.microserviciousuario.feing.ViajeClient;
import com.trabajointegrador.microserviciousuario.mappers.UsuarioMapper;
import com.trabajointegrador.microserviciousuario.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final ViajeClient viajeClient;

    public UsuarioService(ViajeClient viajeClient, UsuarioRepository repo) {
        this.viajeClient = viajeClient;
        this.repo = repo;
    }

    @Transactional
    public UsuarioDTO crearUsuario(UsuarioDTO dto) {
        Usuario usuario = UsuarioMapper.toEntity(dto);

        if (usuario.getFechaRegistro() == null) {
            usuario.setFechaRegistro(LocalDateTime.now());
        }
        Usuario guardado = repo.save(usuario);
        return UsuarioMapper.toDTO(guardado);
    }

    @Transactional
    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO dto) {
        Usuario usuario = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        UsuarioMapper.updateEntity(usuario, dto);

        Usuario guardado = repo.save(usuario);
        return UsuarioMapper.toDTO(guardado);
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarUsuarios() {
        return repo.findAll().stream()
                .map(UsuarioMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioDTO obtenerPorId(Long id) {
        return repo.findById(id)
                .map(UsuarioMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Transactional(readOnly = true)
    public List<UsuarioSimpleDTO> obtenerPorActivo(boolean activo) {
        return repo.findByActivo(activo)
                .stream()
                .map(u -> new UsuarioSimpleDTO(u.getNombreUsuario()))
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioUsoDTO obtenerUso(Long idUsuario, Long idCuenta, LocalDate desde, LocalDate hasta) {

        DateTimeFormatter f = DateTimeFormatter.ISO_LOCAL_DATE;

        ConteoViajesDTO conteo = viajeClient.obtenerConteos(
                idUsuario,
                idCuenta,
                desde.format(f),
                hasta.format(f)
        );

        long cantidadViajesUsuario = conteo.getCantidadViajesUsuario();
        long cantidadViajesCuenta = conteo.getCantidadViajesCuenta();

        boolean hayOtros = cantidadViajesCuenta > cantidadViajesUsuario;

        String nombreUsuario = repo.findById(idUsuario)
                .map(Usuario::getNombreUsuario)
                .orElse("Desconocido");

        return new UsuarioUsoDTO(
                nombreUsuario,
                cantidadViajesUsuario,
                hayOtros
        );
    }

    @Transactional(readOnly = true)
    public List<UsuarioRankingDTO> obtenerTopUsuarios(int anio, Boolean activo) {
        // 1) Traer los viajes del microservicio VIAJES
        List<ViajeDTO> viajes = viajeClient.obtenerViajesPorAnio(anio);
        if (viajes == null || viajes.isEmpty()) {
            return Collections.emptyList();
        }

        // 2) Contar viajes agrupados por idUsuario
        Map<Long, Long> conteos = viajes.stream()
                .collect(Collectors.groupingBy(
                        ViajeDTO::getIdUsuario,
                        Collectors.counting()
                ));

        // 3) Traer solo los usuarios involucrados
        List<Usuario> usuarios = repo.findAllById(conteos.keySet());

        // 4) Filtrar por "activo" si el admin lo pide
        if (activo != null) {
            usuarios = usuarios.stream()
                    .filter(u -> u.isActivo() == activo)
                    .toList();
        }

        // 5) Convertir a DTO
        List<UsuarioRankingDTO> ranking = usuarios.stream()
                .map(u -> new UsuarioRankingDTO(
                        u.getId(),
                        u.getNombreUsuario(),
                        conteos.getOrDefault(u.getId(), 0L)
                ))
                // 6) Orden descendente por viajes
                .sorted(Comparator.comparingLong(UsuarioRankingDTO::getCantidadViajes).reversed())
                // 7) Top 10
                .limit(10)
                .toList();

        return ranking;
    }

    @Transactional
    public void eliminarUsuario(Long id) {
        repo.deleteById(id);
    }
}