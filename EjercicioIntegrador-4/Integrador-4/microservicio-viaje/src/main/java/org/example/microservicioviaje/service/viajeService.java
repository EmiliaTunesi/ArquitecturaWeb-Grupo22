package org.example.microservicioviaje.service;

import org.example.microservicioviaje.entity.viaje;
import org.example.microservicioviaje.repository.viajeRepository;
import org.example.microservicioviaje.DTO.monopatinviajeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.microservicioviaje.feignclients.MonopatinFeignClient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.example.microservicioviaje.DTO.TarifaCalculadaDTO;
import org.example.microservicioviaje.DTO.ConteoViajesDTO;
import org.example.microservicioviaje.DTO.MonopatinUsoDTO;
import org.example.microservicioviaje.feignclients.MonopatinFeignClient;
import org.example.microservicioviaje.feignclients.UsuarioFeignClient;
import org.example.microservicioviaje.feignclients.TarifaFeignClient;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.math.BigDecimal;
import org.example.microservicioviaje.feignclients.CuentaFeignClient;

@Service
public class viajeService {

    @Autowired
    private viajeRepository viajeRepository;

    @Autowired
    private MonopatinFeignClient monopatinFeignClient;

    @Autowired
    private UsuarioFeignClient usuarioFeignClient;
    @Autowired
    private TarifaFeignClient tarifaFeignClient;

    @Autowired
    private CuentaFeignClient cuentaFeignClient;

    // Métodos CRUD
    /**
     * CREAR
     * Guarda un nuevo viaje.
     */
    /**
     * CREAR (INICIAR VIAJE)
     * Valida cuenta activa y monopatín disponible antes de guardar un nuevo viaje.
     */
    public viaje save(viaje viaje) {
        // --- PASO 1: Validar Cuenta Activa ---
        Long idCuenta = viaje.getIdCuenta();
        Boolean cuentaActiva;
        try {
            cuentaActiva = cuentaFeignClient.isCuentaActiva(idCuenta);
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar la disponibilidad de la cuenta: " + e.getMessage());
        }

        if (cuentaActiva == null || !cuentaActiva) {
            // La cuenta NO está disponible (false) o la respuesta fue nula/error.
            throw new RuntimeException("La cuenta con ID " + idCuenta + " no está activa o disponible para iniciar el viaje.");
        }

        // --- PASO 2: Validar Monopatín Disponible ---
        Boolean monopatinDisponible;
        try {
            // Llama al método del Feign client
            monopatinDisponible = monopatinFeignClient.estaDisponible(viaje.getMonopatinId());

        } catch (Exception e) {
            // Si el microservicio de monopatin falla, no crea el viaje
            throw new RuntimeException("Error al consultar disponibilidad del monopatín: " + e.getMessage());
        }

        // 3. Validar la respuesta del monopatín
        if (monopatinDisponible != null && monopatinDisponible) {
            // Ambas validaciones OK: El monopatín SÍ está disponible, guarda el viaje
            return viajeRepository.save(viaje);
        } else {
            // El monopatín NO está disponible (false) o la respuesta fue nula
            throw new RuntimeException("El monopatín con ID " + viaje.getMonopatinId() + " no está disponible.");
        }
    }

    /**
     * Leer - Todos
     * Obtiene todos los viajes.
     */
    public List<viaje> findAll() {
        return viajeRepository.findAll();
    }

    /**
     * READ (Leer - Uno)
     * Busca un viaje por su ID.
     */
    public viaje findById(Long id) {
        return viajeRepository.findById(id).orElse(null);
    }

    /**
     * UPDATE
     * Actualiza un viaje existente basado en su ID.
     */
    @Transactional // si un Feign call falla, no se guarda nada
    public viaje update(Long id, viaje viajeDetails) {

        // 1. Buscar el viaje existente
        viaje viajeExistente = viajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado con ID: " + id));

        // 2. Detectar si el viaje está finalizando en ESTA llamada
        boolean isFinishing = (viajeDetails.getFechaFin() != null &&
                viajeExistente.getFechaFin() == null);

        // 3. Actualizar los campos que llegan en 'viajeDetails'
        // Campos basicos
        viajeExistente.setIdUsuario(viajeDetails.getIdUsuario());
        viajeExistente.setIdCuenta(viajeDetails.getIdCuenta());
        viajeExistente.setMonopatinId(viajeDetails.getMonopatinId());
        viajeExistente.setFechaInicio(viajeDetails.getFechaInicio());
        viajeExistente.setParadaInicioId(viajeDetails.getParadaInicioId());

        // Campos que se actualizan al finalizar viaje
        viajeExistente.setFechaFin(viajeDetails.getFechaFin());
        viajeExistente.setParadaFinId(viajeDetails.getParadaFinId());
        viajeExistente.setKmRecorridos(viajeDetails.getKmRecorridos());
        viajeExistente.setTiempoTotalMinutos(viajeDetails.getTiempoTotalMinutos());
        viajeExistente.setPausaTotalMinutos(viajeDetails.getPausaTotalMinutos());

        // 4.EJECUTAR LÓGICA DE FINALIZACIÓN DEL VIAJE ---
        if (isFinishing) {

            BigDecimal km = viajeExistente.getKmRecorridos();
            int pausaTotal = viajeExistente.getPausaTotalMinutos();
            int tiempoTotal = viajeExistente.getTiempoTotalMinutos();
            // Recolectar datos necesarios para las llamadas
            Long idCuenta = viajeExistente.getIdCuenta();
            Long idMonopatin = viajeExistente.getMonopatinId();

            try {
                // PASO 1: Verificar Usuario Premium ---
                Boolean esPremium = usuarioFeignClient.esUsuarioPremium(idCuenta);

                // PASO 2: Obtener Tarifa
                TarifaCalculadaDTO tarifaCalc = tarifaFeignClient.getTarifaAplicable(pausaTotal);
                BigDecimal costoBase = tarifaCalc.getCosto();

                // PASO 3: Aplicar TarifaId y Calcular Costo Final ---
                viajeExistente.setTarifaId(tarifaCalc.getTarifaId());

                BigDecimal costoFinal;
                if (esPremium != null && esPremium) {
                    // Es premium
                    if (km.compareTo(new BigDecimal(100)) < 0) {
                        // Opción 1: Premium y < 100km
                        costoFinal = BigDecimal.ZERO;
                    } else {
                        // Opción 2: Premium y >= 100km
                        costoFinal = costoBase.divide(new BigDecimal(2), RoundingMode.HALF_UP);
                    }
                } else {
                    // Opción 3: No es premium
                    costoFinal = costoBase;
                }

                viajeExistente.setCostoTotal(costoFinal);

                // --- PASO 4: Actualizar Monopatín (PUT) ---
                // Convertir datos al DTO (Integer/BigDecimal a Double)
                MonopatinUsoDTO usoDTO = new MonopatinUsoDTO(
                        km.doubleValue(),
                        (double) tiempoTotal,
                        (double) pausaTotal
                );

                monopatinFeignClient.actualizarUso(idMonopatin, usoDTO);

            } catch (Exception e) {
                // Si CUALQUIER llamada Feign falla (Usuario, Tarifa, o Monopatin)
                // lanza una excepción para que @Transactional haga rollback
                // y no se guarde el viaje como "finalizado".
                throw new RuntimeException("Error al finalizar el viaje. Detalles: " + e.getMessage(), e);
            }
        }

        // 5. Guardar el viaje (sea una actualización simple o la finalización)
        return viajeRepository.save(viajeExistente);
    }

    /**
     * DELETE
     * Elimina un viaje por su ID.
     */
    public boolean delete(Long id) {
        if (viajeRepository.existsById(id)) {
            viajeRepository.deleteById(id);
            return true;
        }
        return false;
    }
    //devuelve monopatines con mas viajes por año

    public List<monopatinviajeDTO> obtenerMonopatinesConMasDeXViajes(int anio, long cantidadMinima) {
        return viajeRepository.findMonopatinesConMasDeXViajesEnAnio(anio, cantidadMinima);
    }
    // Devuelve todos los viajes de un año específico.
    public List<viaje> findAllByAnio(int anio) {
        return viajeRepository.findAllByAnio(anio);
    }
    //Cuenta los viajes de un usuario especifico, y los de una cuenta especifica en un rango de fechas
    public ConteoViajesDTO getConteoViajes(Long idUsuario, Long idCuenta, String fechaDesde, String fechaHasta)
            throws DateTimeParseException {

        // 1. Parsea las fechas (String a local date)
        LocalDate desde = LocalDate.parse(fechaDesde);
        LocalDate hasta = LocalDate.parse(fechaHasta);

        LocalDateTime start = desde.atStartOfDay();
        LocalDateTime end = hasta.plusDays(1).atStartOfDay();

        // 2. Ejecuta AMBAS consultas
        long countUsuario = viajeRepository.countByUsuarioAndFecha(idUsuario, start, end);
        long countCuenta = viajeRepository.countByCuentaAndFecha(idCuenta, start, end);

        // 3. Construye y devuelve el DTO
        return new ConteoViajesDTO(countUsuario, countCuenta);
    }
    public BigDecimal getTotalFacturado(int anio, int mesInicio, int mesFin) {
        BigDecimal total = viajeRepository.findTotalFacturadoEnRango(anio, mesInicio, mesFin);
        // Si no hay viajes en ese rango, SUM() devuelve NULL.
        // Convierte a Cero (BigDecimal.ZERO).
        if (total == null) {
            return BigDecimal.ZERO;
        }
        return total;
    }
}