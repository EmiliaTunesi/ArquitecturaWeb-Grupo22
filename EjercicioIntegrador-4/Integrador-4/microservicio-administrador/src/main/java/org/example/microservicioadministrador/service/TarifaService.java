package org.example.microservicioadministrador.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.microservicioadministrador.dto.request.TarifaRequestDTO;
import org.example.microservicioadministrador.dto.response.TarifaResponseDTO;
import org.example.microservicioadministrador.entity.Tarifa;
import org.example.microservicioadministrador.entity.tipoTarifa;
import org.example.microservicioadministrador.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TarifaService {

    private final TarifaRepository tarifaRepository;

    @Transactional
    public TarifaResponseDTO ajustarPrecio(TarifaRequestDTO tarifaDTO) throws Exception {
        try {
            // Buscar si existe una tarifa del mismo tipo
            Optional<Tarifa> tarifaExistente = tarifaRepository.findByTipo(tarifaDTO.getTipo());

            if (tarifaExistente.isPresent()) {
                // Si existe, actualizar
                Tarifa tarifa = tarifaExistente.get();
                tarifa.setPrecio_min(tarifaDTO.getPrecio_min());
                tarifa.setVigenteDesde(tarifaDTO.getVigenteDesde());
                tarifa.setVigenteHasta(tarifaDTO.getVigenteHasta());
                tarifa.setTiempoEspera(tarifaDTO.getTiempoEspera());

                Tarifa tarifaActualizada = tarifaRepository.save(tarifa);
                return convertirADTO(tarifaActualizada);
            } else {
                // Si no existe, crear nueva
                Tarifa nuevaTarifa = new Tarifa();
                nuevaTarifa.setNombre(tarifaDTO.getNombre());
                nuevaTarifa.setPrecio_min(tarifaDTO.getPrecio_min());
                nuevaTarifa.setTipo(tarifaDTO.getTipo());
                nuevaTarifa.setTiempoEspera(tarifaDTO.getTiempoEspera());
                nuevaTarifa.setVigenteDesde(tarifaDTO.getVigenteDesde());
                nuevaTarifa.setVigenteHasta(tarifaDTO.getVigenteHasta());

                Tarifa tarifaGuardada = tarifaRepository.save(nuevaTarifa);
                return convertirADTO(tarifaGuardada);
            }
        } catch (Exception e) {
            throw new Exception("Error al ajustar el precio: " + e.getMessage());
        }
    }

    @Transactional
    public TarifaResponseDTO obtenerTarifaAplicable() throws Exception {
        try {
            LocalDate fechaActual = LocalDate.now();

            // 1. Verificar si hay alguna promoción vigente
            Optional<Tarifa> tarifaPromocional = tarifaRepository.findTarifaPromocionalVigente(fechaActual);
            if (tarifaPromocional.isPresent()) {
                return convertirADTO(tarifaPromocional.get());
            }

            // 2. Si no hay promoción, retornar la tarifa normal
            Tarifa tarifaNormal = tarifaRepository.findByTipo(tipoTarifa.NORMAL)
                    .orElseThrow(() -> new Exception("No existe tarifa normal configurada"));

            return convertirADTO(tarifaNormal);

        } catch (Exception e) {
            throw new Exception("Error al obtener tarifa aplicable: " + e.getMessage());
        }
    }


    @Transactional
    public TarifaResponseDTO save(TarifaRequestDTO tarifaDTO) throws Exception {
        try {
            // 1. Convertir el DTO a Entidad
            Tarifa tarifa = new Tarifa();
            tarifa.setNombre(tarifaDTO.getNombre());
            tarifa.setPrecio_min(tarifaDTO.getPrecio_min());
            tarifa.setTipo(tarifaDTO.getTipo());
            tarifa.setTiempoEspera(tarifaDTO.getTiempoEspera());
            tarifa.setVigenteDesde(tarifaDTO.getVigenteDesde());
            tarifa.setVigenteHasta(tarifaDTO.getVigenteHasta());

            // 2. Guardar la entidad
            Tarifa tarifaGuardada = tarifaRepository.save(tarifa);

            // 3. Convertir la entidad guardada a DTO de respuesta
            TarifaResponseDTO responseDTO = new TarifaResponseDTO();
            responseDTO.setId(tarifaGuardada.getId());
            responseDTO.setNombre(tarifaGuardada.getNombre());
            responseDTO.setPrecio_min(tarifaGuardada.getPrecio_min());
            responseDTO.setTipo(tarifaGuardada.getTipo());
            responseDTO.setTiempoEspera(tarifaGuardada.getTiempoEspera());
            responseDTO.setVigenteDesde(tarifaGuardada.getVigenteDesde());
            responseDTO.setVigenteHasta(tarifaGuardada.getVigenteHasta());

            return responseDTO;
        } catch (Exception e) {
            throw new Exception("Error al guardar la tarifa: " + e.getMessage());
        }
    }

    @Transactional
    public TarifaResponseDTO update(Long id, TarifaRequestDTO tarifaDTO) throws Exception {
        try {
            // 1. Verificar si existe la tarifa
            Tarifa tarifaExistente = tarifaRepository.findById(id)
                    .orElseThrow(() -> new Exception("No existe tarifa con ID: " + id));

            // 2. Actualizar los datos
            tarifaExistente.setNombre(tarifaDTO.getNombre());
            tarifaExistente.setPrecio_min(tarifaDTO.getPrecio_min());
            tarifaExistente.setTipo(tarifaDTO.getTipo());
            tarifaExistente.setTiempoEspera(tarifaDTO.getTiempoEspera());
            tarifaExistente.setVigenteDesde(tarifaDTO.getVigenteDesde());
            tarifaExistente.setVigenteHasta(tarifaDTO.getVigenteHasta());

            // 3. Guardar los cambios
            Tarifa tarifaActualizada = tarifaRepository.save(tarifaExistente);

            // 4. Convertir a DTO de respuesta
            TarifaResponseDTO responseDTO = new TarifaResponseDTO();
            responseDTO.setId(tarifaActualizada.getId());
            responseDTO.setNombre(tarifaActualizada.getNombre());
            responseDTO.setPrecio_min(tarifaActualizada.getPrecio_min());
            responseDTO.setTipo(tarifaActualizada.getTipo());
            responseDTO.setTiempoEspera(tarifaActualizada.getTiempoEspera());
            responseDTO.setVigenteDesde(tarifaActualizada.getVigenteDesde());
            responseDTO.setVigenteHasta(tarifaActualizada.getVigenteHasta());

            return responseDTO;
        } catch (Exception e) {
            throw new Exception("Error al actualizar la tarifa: " + e.getMessage());
        }
    }
    @Transactional
    public List<TarifaResponseDTO> findAll() throws Exception {
        try {
            // 1. Obtener todas las tarifas
            List<Tarifa> tarifas = tarifaRepository.findAll();
            List<TarifaResponseDTO> responseDTOs = new ArrayList<>();

            // 2. Convertir cada entidad a DTO
            for (Tarifa tarifa : tarifas) {
                TarifaResponseDTO dto = new TarifaResponseDTO();
                dto.setId(tarifa.getId());
                dto.setNombre(tarifa.getNombre());
                dto.setPrecio_min(tarifa.getPrecio_min());
                dto.setTipo(tarifa.getTipo());
                dto.setTiempoEspera(tarifa.getTiempoEspera());
                dto.setVigenteDesde(tarifa.getVigenteDesde());
                dto.setVigenteHasta(tarifa.getVigenteHasta());
                responseDTOs.add(dto);
            }

            return responseDTOs;
        } catch (Exception e) {
            throw new Exception("Error al obtener las tarifas: " + e.getMessage());
        }
    }

    @Transactional
    public TarifaResponseDTO findById(Long id) throws Exception {
        try {
            // 1. Buscar la tarifa
            Tarifa tarifa = tarifaRepository.findById(id)
                    .orElseThrow(() -> new Exception("No existe tarifa con ID: " + id));

            // 2. Convertir a DTO de respuesta
            TarifaResponseDTO responseDTO = new TarifaResponseDTO();
            responseDTO.setId(tarifa.getId());
            responseDTO.setNombre(tarifa.getNombre());
            responseDTO.setPrecio_min(tarifa.getPrecio_min());
            responseDTO.setTipo(tarifa.getTipo());
            responseDTO.setTiempoEspera(tarifa.getTiempoEspera());
            responseDTO.setVigenteDesde(tarifa.getVigenteDesde());
            responseDTO.setVigenteHasta(tarifa.getVigenteHasta());

            return responseDTO;
        } catch (Exception e) {
            throw new Exception("Error al buscar la tarifa: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Long id) throws Exception {
        try {
            // 1. Verificar si existe la tarifa
            if (!tarifaRepository.existsById(id)) {
                throw new Exception("No existe tarifa con ID: " + id);
            }

            // 2. Eliminar la tarifa
            tarifaRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Error al eliminar la tarifa: " + e.getMessage());
        }
    }

    private TarifaResponseDTO convertirADTO(Tarifa tarifa) {
        TarifaResponseDTO responseDTO = new TarifaResponseDTO();
        responseDTO.setId(tarifa.getId());
        responseDTO.setNombre(tarifa.getNombre());
        responseDTO.setPrecio_min(tarifa.getPrecio_min());
        responseDTO.setTipo(tarifa.getTipo());
        responseDTO.setTiempoEspera(tarifa.getTiempoEspera());
        responseDTO.setVigenteDesde(tarifa.getVigenteDesde());
        responseDTO.setVigenteHasta(tarifa.getVigenteHasta());

        return responseDTO;
    }
}