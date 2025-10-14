package com.arquitecturaweb.ejercicioIntegrador3.service;


import com.arquitecturaweb.ejercicioIntegrador3.dto.request.CarreraRequestDTO;
import com.arquitecturaweb.ejercicioIntegrador3.dto.response.CarreraResponseDTO;
import com.arquitecturaweb.ejercicioIntegrador3.entity.Carrera;
import com.arquitecturaweb.ejercicioIntegrador3.repository.CarreraRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CarreraService {

    private CarreraRepository carreraRepository;


    @Transactional
    public CarreraResponseDTO save(CarreraRequestDTO carreraDTO) throws Exception {
        try {
            // Convertir DTO a Entidad
            Carrera carrera = new Carrera();
            carrera.setNombre(carreraDTO.getNombre());
            carrera.setDuracion(carreraDTO.getDuracion());

            // Guardar
            Carrera carreraGuardada = carreraRepository.save(carrera);

            // Convertir Entidad a DTO de respuesta
            CarreraResponseDTO responseDTO = new CarreraResponseDTO();
            responseDTO.setId_carrera(carreraGuardada.getId_carrera());
            responseDTO.setNombre(carreraGuardada.getNombre());
            responseDTO.setDuracion(carreraGuardada.getDuracion());

            return responseDTO;
        } catch (Exception e) {
            throw new Exception("Error al guardar la carrera: " + e.getMessage());
        }
    }

}
