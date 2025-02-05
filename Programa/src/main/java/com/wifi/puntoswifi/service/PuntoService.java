package com.wifi.puntoswifi.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wifi.puntoswifi.model.IPuntoDTO;

import com.wifi.puntoswifi.model.PuntoDistanciaDTO;
import com.wifi.puntoswifi.model.WifiAccessPoint;
import com.wifi.puntoswifi.repository.IPuntoRepository;

import io.swagger.v3.oas.annotations.Operation;

@Service
public class PuntoService {
    
    private final IPuntoRepository puntoRepository;

    public PuntoService(IPuntoRepository puntoRepository) {
        this.puntoRepository = puntoRepository;
    }

    @Operation(summary = "Obtiene lista paginada de puntos WIFI almacenados en DB")
    public Page<WifiAccessPoint> findAll(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return puntoRepository.findAll(paging);
    }

    @Operation(summary = "Obtiene determinado punto WIFI a través de su ID")
    public Optional<WifiAccessPoint> findById(String id) {
        return puntoRepository.findById(id).stream().findFirst();
    }

    @Operation(summary = "Obtiene lista paginada de puntos WIFI por Colonia")
    public Page<WifiAccessPoint> findByColonia(String colonia, int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return puntoRepository.findByColonia(colonia, paging);
    }

    @Operation(summary = "Obtiene lista paginada de puntos WIFI ordenados por distancia")
    public Page<IPuntoDTO> findByLatitudAndLongitud(double latitud, double longitud, int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return puntoRepository.findByLatitudAndLongitud(latitud, longitud, paging);
    }

    // 🚀 Versión funcional optimizada
    @Operation(summary = "Obtiene lista paginada de puntos WIFI en orden de menor a mayor distancia con programación funcional")
    public List<WifiAccessPoint> findByLatitudAndLongitudFP(double latitud, double longitud, int pageNo, int pageSize) {
        return StreamSupport.stream(puntoRepository.findAll().spliterator(), false)
                .map(punto -> new PuntoDistanciaDTO(punto, calcularDistancia(punto, latitud, longitud)))
                .sorted(Comparator.comparingDouble(PuntoDistanciaDTO::getDistancia))
                .skip((long) pageNo * pageSize)
                .limit(pageSize)
                .map(PuntoDistanciaDTO::getPunto)
                .collect(Collectors.toList());
    }

    private double calcularDistancia(WifiAccessPoint punto, double latitud, double longitud) {
        return Math.sqrt(Math.pow(punto.getLatitud() - latitud, 2) + Math.pow(punto.getLongitud() - longitud, 2));
    }
}
