package com.wifi.puntoswifi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wifi.puntoswifi.model.IPuntoDTO;
import com.wifi.puntoswifi.model.WifiAccessPoint;

import io.swagger.v3.oas.annotations.Operation;


@Repository
public interface IPuntoRepository extends PagingAndSortingRepository<WifiAccessPoint, Long> {

    @Operation(summary = "Obtener todos los Puntos WIFI con paginación")
    Page<WifiAccessPoint> findAll(Pageable pageable);

    @Operation(summary = "Obtener un Punto WIFI por su ID")
    Optional<WifiAccessPoint> findById(String id);  // ID de la tabla PUNTO (clave primaria)

    @Operation(summary = "Obtener todos los Puntos WIFI de una Colonia específica con paginación")
    Page<WifiAccessPoint> findByColonia(String colonia, Pageable pageable);

    @Operation(summary = "Obtener Puntos WIFI ordenados por distancia desde una ubicación dada")
    @Query(value = """
        SELECT 
            (6371 * ACOS(COS(RADIANS(:latitude)) * COS(RADIANS(p.latitud)) * 
            COS(RADIANS(p.longitud) - RADIANS(:longitude)) + SIN(RADIANS(:latitude)) * 
			    SIN(RADIANS(p.latitud)))) AS distance,
            p.dbid, p.alcaldia, p.colonia, p.fecha_instalacion, 
            p.id, p.latitud, p.longitud, p.programa
        FROM PUNTO p
        ORDER BY distance ASC
        """,
        countQuery = "SELECT COUNT(*) FROM PUNTO",
        nativeQuery = true)
    Page<IPuntoDTO> findByLatitudAndLongitud(
        @Param("latitud") Double latitud,
        @Param("longitud") Double longitud,
        Pageable pageable
    );
}


