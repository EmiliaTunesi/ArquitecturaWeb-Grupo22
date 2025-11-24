package org.example.microservicioviaje.repository;

import org.example.microservicioviaje.entity.viaje;
import org.example.microservicioviaje.DTO.monopatinviajeDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;

import java.util.List;
@Repository
public interface viajeRepository extends JpaRepository<viaje, Long> {
    //Obtiene monopatines que tuvieron MÁS de 'cantidadMinima' viajes en un año.
    @Query("""
    SELECT new org.example.microservicioviaje.DTO.monopatinviajeDTO(
        v.monopatinId, COUNT(v)
    )
    FROM viaje v
    WHERE YEAR(v.fechaInicio) = :anio
    GROUP BY v.monopatinId
    HAVING COUNT(v) > :cantidadMinima
    ORDER BY COUNT(v) DESC
    """)
    List<monopatinviajeDTO> findMonopatinesConMasDeXViajesEnAnio(
            @Param("anio") int anio,
            @Param("cantidadMinima") long cantidadMinima
    );

    // Busca todos los viajes que ocurrieron en un año específic (basado en la fecha de inicio).
    @Query("""
    SELECT v
    FROM viaje v
    WHERE YEAR(v.fechaInicio) = :anio
    """)
    List<viaje> findAllByAnio(@Param("anio") int anio);

        //Cuenta los viajes de un USUARIO específico en un rango de fechas.
        @Query("""
    SELECT COUNT(v) FROM viaje v
    WHERE v.idUsuario = :idUsuario
    AND v.fechaInicio >= :fechaDesde
    AND v.fechaInicio < :fechaHasta
    """)
        long countByUsuarioAndFecha(
                @Param("idUsuario") Long idUsuario,
                @Param("fechaDesde") LocalDateTime fechaDesde,
                @Param("fechaHasta") LocalDateTime fechaHasta
        );

        //Cuenta los viajes de una CUENTA específica en un rango de fechas.
        @Query("""
    SELECT COUNT(v) FROM viaje v
    WHERE v.idCuenta = :idCuenta
    AND v.fechaInicio >= :fechaDesde
    AND v.fechaInicio < :fechaHasta
    """)
        long countByCuentaAndFecha(
                @Param("idCuenta") Long idCuenta,
                @Param("fechaDesde") LocalDateTime fechaDesde,
                @Param("fechaHasta") LocalDateTime fechaHasta
        );
    //Suma la columna 'costoTotal' de los viajes finalizados en un rango de meses de un año específico.

    @Query("""
    SELECT SUM(v.costoTotal)
    FROM viaje v
    WHERE v.costoTotal IS NOT NULL
      AND v.fechaFin IS NOT NULL
      AND YEAR(v.fechaFin) = :anio
      AND MONTH(v.fechaFin) BETWEEN :mesInicio AND :mesFin
    """)
    BigDecimal findTotalFacturadoEnRango(
            @Param("anio") int anio,
            @Param("mesInicio") int mesInicio,
            @Param("mesFin") int mesFin
    );

    @Query(value = "SELECT * FROM viaje", nativeQuery = true)
    List<Object[]> findAllViajes();

    @Query(value = "SELECT * FROM viaje WHERE id_usuario = :idUsuario", nativeQuery = true)
    List<Object[]> findAllByUsuario(@Param("idUsuario") Long idUsuario);

}






