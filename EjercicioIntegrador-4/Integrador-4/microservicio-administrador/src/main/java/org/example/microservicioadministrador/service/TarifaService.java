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
    public TarifaResponseDTO obtenerTarifaAplicable(Integer tiempo) throws Exception {
        try {
            Tarifa vigente = tarifaRepository.findTarifaPromocionalVigente()
                    .orElseThrow(() -> new Exception("No existe tarifa vigente configurada"));
            if (tiempo > vigente.getTiempoEspera()) {
                Tarifa esPausa = tarifaRepository.findByTipo(tipoTarifa.PAUSA)
                        .orElseThrow(() -> new Exception("No existe tarifa espera configurada"));
                return convertirTarifaResponseDTO(esPausa);
            }
            return convertirTarifaResponseDTO(vigente);

        }catch (Exception e) {
            throw new Exception("Error al obtener tarifa aplicable: " + e.getMessage(), e);
        }
    }

    @Transactional
    public TarifaResponseDTO save(TarifaRequestDTO tarifaDTO) throws Exception {
        try {
            Tarifa tarifa = convertirRequestAtarifa(tarifaDTO);

            tarifaRepository.save(tarifa);

            TarifaResponseDTO responseDTO = convertirTarifaResponseDTO(tarifa);

            return responseDTO;
        } catch (Exception e) {
            throw new Exception("Error al guardar la tarifa: " + e.getMessage());
        }
    }

    @Transactional
    public TarifaResponseDTO update(Long id, TarifaRequestDTO tarifaDTO) throws Exception {
        try {
            Tarifa tarifaExistente = tarifaRepository.findById(id)
                    .orElseThrow(() -> new Exception("No existe tarifa con ID: " + id));

            tarifaExistente.setNombre(tarifaDTO.getNombre());
            tarifaExistente.setPrecio_min(tarifaDTO.getPrecio_min());
            tarifaExistente.setTipo(tarifaDTO.getTipo());
            tarifaExistente.setTiempoEspera(tarifaDTO.getTiempoEspera());
            tarifaExistente.setVigenteDesde(tarifaDTO.getVigenteDesde());
            tarifaExistente.setVigenteHasta(tarifaDTO.getVigenteHasta());

            Tarifa tarifaActualizada = tarifaRepository.save(tarifaExistente);

            TarifaResponseDTO responseDTO = convertirTarifaResponseDTO(tarifaActualizada);

            return responseDTO;
        } catch (Exception e) {
            throw new Exception("Error al actualizar la tarifa: " + e.getMessage());
        }
    }
    @Transactional
    public List<TarifaResponseDTO> findAll() throws Exception {
        try {
            List<Tarifa> tarifas = tarifaRepository.findAll();
            List<TarifaResponseDTO> responseDTOs = new ArrayList<>();

            for (Tarifa tarifa : tarifas) {
                TarifaResponseDTO dto = convertirTarifaResponseDTO(tarifa);
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
            Tarifa tarifa = tarifaRepository.findById(id)
                    .orElseThrow(() -> new Exception("No existe tarifa con ID: " + id));

            TarifaResponseDTO responseDTO = convertirTarifaResponseDTO(tarifa);

            return responseDTO;
        } catch (Exception e) {
            throw new Exception("Error al buscar la tarifa: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Long id) throws Exception {
        try {
            if (!tarifaRepository.existsById(id)) {
                throw new Exception("No existe tarifa con ID: " + id);
            }

            tarifaRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Error al eliminar la tarifa: " + e.getMessage());
        }
    }
    @Transactional
    public void deleteTotal() throws Exception {
        try {
            tarifaRepository.deleteAll();
        } catch (Exception e) {
            throw new Exception("Error"+ e.getMessage());
        }
    }

    @Transactional
    public TarifaResponseDTO findByTipo(tipoTarifa tipo) throws Exception {
        try {
            Tarifa tarifa = tarifaRepository.findByTipo(tipo)
                    .orElseThrow(() -> new Exception("No existe tarifa con tipo: " + tipo));

            TarifaResponseDTO responseDTO = convertirTarifaResponseDTO(tarifa);

            return responseDTO;
        } catch (Exception e) {
            throw new Exception("Error al buscar la tarifa: " + e.getMessage());
        }
    }
    private TarifaResponseDTO convertirTarifaResponseDTO(Tarifa tarifa) {
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
    private Tarifa convertirRequestAtarifa(TarifaRequestDTO dto) {
        Tarifa tarifa = new Tarifa();
        tarifa.setNombre(dto.getNombre());
        tarifa.setPrecio_min(dto.getPrecio_min());
        tarifa.setTipo(dto.getTipo());
        tarifa.setTiempoEspera(dto.getTiempoEspera());
        tarifa.setVigenteDesde(dto.getVigenteDesde());
        tarifa.setVigenteHasta(dto.getVigenteHasta());

        return tarifa;
    }


}