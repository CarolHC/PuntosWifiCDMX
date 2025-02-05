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

import com.wifi.puntoswifi.model.IPuntoDTO;
import com.wifi.puntoswifi.model.WifiAccessPoint;
import com.wifi.puntoswifi.service.PuntoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/puntos")
@CrossOrigin(origins = "*")
@Tag(name = "Puntos WIFI", description = "Controller para operaciones sobre puntos WIFI en Ciuda de Mexico")
public class PuntosWifiController
{
	private final PuntoService puntoService;

	public PuntosWifiController(PuntoService puntoService)
	{
		this.puntoService = puntoService;
	}

	@Operation(summary = "Obtiene lista paginada de puntos WIFI almacenados en DB")
	@Parameter(name = "page", example = "page", required = false)
	@Parameter(name = "size", example = "size", required = false)
	@GetMapping(produces = "application/json")
	public Page<WifiAccessPoint> findAll(
			@RequestParam(value = "page", defaultValue = "0", required = false) int page,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size
	)
	{
		return puntoService.findAll(page, size);
	}

	@Operation(summary = "Obtiene determinado punto WIFI a través de parámetro")
	@Parameter(name = "id", example = "123", required = true)
	@GetMapping(value = "/{id}", produces = "application/json")
	public WifiAccessPoint findById(@PathVariable("id") String id) {
	    return puntoService.findById(id)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Punto WIFI no encontrado"));
	}

	@Operation(summary = "Obtiene lista paginada de puntos WIFI a través de Colonia específica")
	@Parameter(name = "colonia", example = "Nimajuyu", required = true)
	@Parameter(name = "page", example = "page", required = false)
	@Parameter(name = "size", example = "size", required = false)
	@GetMapping(value = "/colonias/{colonia}", produces = "application/json")
	public Page<WifiAccessPoint> findByColonia(
	        @PathVariable("colonia") String colonia,
	        @RequestParam(value = "page", defaultValue = "0", required = false) int page,
	        @RequestParam(value = "size", defaultValue = "10", required = false) int size
	)
	{
	    return puntoService.findByColonia(colonia, page, size);
	}


	@Operation(summary = "Obtiene lista paginada de puntos WIFI en orden de menor a mayor distancia")
	@Parameter(name = "latitud", example = "19.4443523", required = true)
	@Parameter(name = "longitud", example = "-99.1307745", required = true)
	@Parameter(name = "page", example = "page", required = false)
	@Parameter(name = "size", example = "size", required = false)
	@GetMapping(value = "/latitudes/{latitud}/longitudes/{longitud}", produces = "application/json")
	public Page<IPuntoDTO> findByLatitudAndLongitud(
	        @PathVariable("latitud") Double latitud, @PathVariable("longitud") Double longitud,
	        @RequestParam(value = "page", defaultValue = "0", required = false) int page,
	        @RequestParam(value = "size", defaultValue = "10", required = false) int size
	)
	{
	    return puntoService.findByLatitudAndLongitud(latitud, longitud, page, size);
	}
}

