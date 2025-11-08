package com.trabajointegrador.microserviciousuario.service;

import com.trabajointegrador.microserviciousuario.dto.UsuarioDTO;
import com.trabajointegrador.microserviciousuario.dto.UsuarioSimpleDTO;
import com.trabajointegrador.microserviciousuario.dto.UsuarioUsoDTO;
import com.trabajointegrador.microserviciousuario.dto.ViajeDTO;
import com.trabajointegrador.microserviciousuario.entity.Usuario;
import com.trabajointegrador.microserviciousuario.feing.ViajeClient;
import com.trabajointegrador.microserviciousuario.mappers.UsuarioMapper;
import com.trabajointegrador.microserviciousuario.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@Slf4j
public class UsuarioService {

    private final UsuarioRepository repo;
    private final ViajeClient viajeClient;

    public UsuarioService(ViajeClient viajeClient, UsuarioRepository repo) {
        this.viajeClient = viajeClient;
        this.repo = repo;
    }

    public UsuarioDTO crearUsuario(UsuarioDTO dto) {
        Usuario usuario = UsuarioMapper.toEntity(dto);

        if (usuario.getFechaRegistro() == null) {
            usuario.setFechaRegistro(LocalDateTime.now());
        }
        Usuario guardado = repo.save(usuario);
        return UsuarioMapper.toDTO(guardado);
    }

    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO dto) {
        Usuario usuario = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        UsuarioMapper.updateEntity(usuario, dto);

        Usuario guardado = repo.save(usuario);
        return UsuarioMapper.toDTO(guardado);
    }

    public List<UsuarioDTO> listarUsuarios() {
        return repo.findAll().stream()
                .map(UsuarioMapper::toDTO)
                .toList();
    }

    public UsuarioDTO obtenerPorId(Long id) {
        return repo.findById(id)
                .map(UsuarioMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public List<UsuarioSimpleDTO> obtenerPorActivo(boolean activo) {
        return repo.findByActivo(activo)
                .stream()
                .map(u -> new UsuarioSimpleDTO(u.getNombreUsuario()))
                .toList();
    }

    public UsuarioUsoDTO obtenerUso(Long idUsuario, Long idCuenta, LocalDate desde, LocalDate hasta) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        // 1️⃣ Pedimos viajes de la cuenta en el período
        List<ViajeDTO> viajes = viajeClient.obtenerViajesPorCuenta(
                idCuenta,
                desde.format(formatter),
                hasta.format(formatter)
        );

        // 2️⃣ Calculamos kilómetros y cantidad de viajes del usuario
        double totalKm = viajes.stream()
                .filter(v -> v.getIdUsuario().equals(idUsuario))
                .mapToDouble(ViajeDTO::getKilometros)
                .sum();

        long cantidadViajes = viajes.stream()
                .filter(v -> v.getIdUsuario().equals(idUsuario))
                .count();

        // 3️⃣ Obtenemos otros usuarios de la misma cuenta que hicieron viajes
        List<String> otrosUsuarios = viajes.stream()
                .filter(v -> !v.getIdUsuario().equals(idUsuario))
                .map(ViajeDTO::getIdUsuario)
                .distinct()
                .map(repo::findById)
                .filter(java.util.Optional::isPresent)
                .map(opt -> opt.get().getNombreUsuario())
                .toList();

        // 4️⃣ Obtenemos nombreUsuario del solicitante
        String nombreUsuario = repo.findById(idUsuario)
                .map(Usuario::getNombreUsuario)
                .orElse("Desconocido");

        return new UsuarioUsoDTO(nombreUsuario, totalKm, cantidadViajes, otrosUsuarios);
    }

    public void eliminarUsuario(Long id) {
        repo.deleteById(id);
    }
}