package com.wifi.puntoswifi.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.wifi.puntoswifi.model.WifiAccessPoint;
import com.wifi.puntoswifi.service.PuntoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/puntosfp")
@CrossOrigin(origins = "*")
@Tag(
        name = "Puntos WIFI Programación funcional",
        description = "Controlador para operaciones a través de programación funcional sobre puntos WIFI en Ciudad de México")
public class ControllerPF {

    private final PuntoService puntoService;

    public ControllerPF(PuntoService puntoService) {
        this.puntoService = puntoService;
    }

    @Operation(summary = "Obtiene lista paginada de puntos WIFI almacenados en DB")
    @Parameter(name = "page", example = "page", required = false)
    @Parameter(name = "size", example = "size", required = false)
    @GetMapping(produces = "application/json")
    public Page<WifiAccessPoint> findAllFP(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size
    ) {
        return puntoService.findAll(page, size); 
    }

    @Operation(summary = "Obtiene determinado punto WIFI a través de parámetro")
    @Parameter(name = "id", example = "123", required = true)
    @GetMapping(value = "/{id}", produces = "application/json")
    public WifiAccessPoint findByIdFP(@PathVariable("id") String id) {
        Optional<WifiAccessPoint> punto = puntoService.findById(id);
        return punto.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Punto WIFI con ID " + id + " no encontrado"));
    }

    @Operation(summary = "Obtiene lista paginada de puntos WIFI a través de colonia específica")
    @Parameter(name = "colonia", example = "Nimajuyu", required = true)
    @Parameter(name = "page", example = "page", required = false)
    @Parameter(name = "size", example = "size", required = false)
    @GetMapping(value = "/colonias/{colonia}", produces = "application/json")
    public List<WifiAccessPoint> findByColoniaFP(
            @PathVariable("colonia") String colonia, 
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size
    ) {
        return (List<WifiAccessPoint>) puntoService.findByColonia(colonia, page, size);
    }

    @Operation(summary = "Obtiene lista paginada de puntos WIFI en orden de menor a mayor distancia")
    @Parameter(name = "latitud", example = "19.4443523", required = true)
    @Parameter(name = "longitud", example = "-99.1307745", required = true)
    @Parameter(name = "page", example = "page", required = false)
    @Parameter(name = "size", example = "size", required = false)
    @GetMapping(value = "/latitudes/{latitud}/longitudes/{longitud}", produces = "application/json")
    public List<WifiAccessPoint> findByLatitudAndLongitudFP(
            @PathVariable("latitud") Double latitud, 
            @PathVariable("longitud") Double longitud,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size
    ) {
        return puntoService.findByLatitudAndLongitudFP(latitud, longitud, page, size);
    }
}

