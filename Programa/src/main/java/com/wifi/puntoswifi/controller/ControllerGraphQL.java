package com.wifi.puntoswifi.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.webjars.NotFoundException;

import com.wifi.puntoswifi.model.IPuntoDTO;
import com.wifi.puntoswifi.model.WifiAccessPoint;
import com.wifi.puntoswifi.service.PuntoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@Tag(name = "Puntos WIFI GraphQL", description = "Controlador para operaciones sobre puntos WIFI en Ciudad de México con GraphQL")
public class ControllerGraphQL {
	private final PuntoService puntoService;

	public ControllerGraphQL(PuntoService puntoService) {
		this.puntoService = puntoService;
	}

	@Operation(summary = "Obtiene lista paginada de puntos WIFI almacenados en DB")
	@Parameter(name = "page", example = "0", required = false)
	@Parameter(name = "size", example = "10", required = false)
	@QueryMapping
	public Page<WifiAccessPoint> puntosFindAll(@Argument Integer page, @Argument Integer size) {
		// Establecer valores por defecto si page o size son nulos
		page = page != null ? page : 0;
		size = size != null ? size : 10;
		return puntoService.findAll(page, size);
	}

	@Operation(summary = "Obtiene determinado punto WIFI a través de parámetro")
	@Parameter(name = "id", example = "123", required = true)
	@QueryMapping
	public WifiAccessPoint puntosFindById(@Argument String id) {
	    return puntoService.findById(id)
	            .orElseThrow(() -> new NotFoundException("Punto WIFI con ID " + id + " no encontrado."));
	}

	@Operation(summary = "Obtiene lista paginada de puntos WIFI a través de colonia específica")
	@Parameter(name = "colonia", example = "Nimajuyu", required = true)
	@Parameter(name = "page", example = "0", required = false)
	@Parameter(name = "size", example = "10", required = false)
	@QueryMapping
	public Page<WifiAccessPoint> puntosFindByColonia(@Argument String colonia, @Argument Integer page,
			@Argument Integer size) {
		page = page != null ? page : 0;
		size = size != null ? size : 10;
		return puntoService.findByColonia(colonia, page, size);
	}

	@Operation(summary = "Obtiene lista paginada de puntos WIFI en orden de menor a mayor distancia")
	@Parameter(name = "latitud", example = "19.4443523", required = true)
	@Parameter(name = "longitud", example = "-99.1307745", required = true)
	@Parameter(name = "page", example = "0", required = false)
	@Parameter(name = "size", example = "10", required = false)
	@QueryMapping
	public Page<IPuntoDTO> puntosFindByLatitudAndLongitud(@Argument Float latitud, @Argument Float longitud,
			@Argument Integer page, @Argument Integer size) {
		page = page != null ? page : 0;
		size = size != null ? size : 10;
		return puntoService.findByLatitudAndLongitud(latitud.doubleValue(), longitud.doubleValue(), page, size);
	}

	@Operation(summary = "Obtiene lista paginada de puntos WIFI almacenados en DB con programación funcional")
	@Parameter(name = "page", example = "0", required = false)
	@Parameter(name = "size", example = "10", required = false)
	@QueryMapping
	public Page<WifiAccessPoint> puntosFindAllFP(@Argument Integer page, @Argument Integer size) {
		page = page != null ? page : 0;
		size = size != null ? size : 10;
		return puntoService.findAll(page, size); // Ajuste si el servicio tiene programación funcional
	}

	@Operation(summary = "Obtiene determinado punto WIFI a través de parámetro con programación funcional")
	@Parameter(name = "id", example = "123", required = true)
	@QueryMapping
	public Optional<WifiAccessPoint> puntosFindByIdFP(@Argument String id) {
		Optional<WifiAccessPoint> punto = puntoService.findById(id);
		if (punto == null) {
			throw new NotFoundException("Punto WIFI con ID " + id + " no encontrado.");
		}
		return punto;
	}

	@Operation(summary = "Obtiene lista paginada de puntos WIFI a través de colonia específica con programación funcional")
	@Parameter(name = "colonia", example = "Nimajuyu", required = true)
	@Parameter(name = "page", example = "0", required = false)
	@Parameter(name = "size", example = "10", required = false)
	@QueryMapping
	public List<WifiAccessPoint> puntosFindByColoniaFP(@Argument String colonia, @Argument Integer page,
			@Argument Integer size) {
		page = page != null ? page : 0;
		size = size != null ? size : 10;
		return (List<WifiAccessPoint>) puntoService.findByColonia(colonia, page, size);
	}

	@Operation(summary = "Obtiene lista paginada de puntos WIFI en orden de menor a mayor distancia con programación funcional")
	@Parameter(name = "latitud", example = "19.4443523", required = true)
	@Parameter(name = "longitud", example = "-99.1307745", required = true)
	@Parameter(name = "page", example = "0", required = false)
	@Parameter(name = "size", example = "10", required = false)
	@QueryMapping
	public List<WifiAccessPoint> puntosFindByLatitudAndLongitudFP(@Argument Float latitud, @Argument Float longitud,
			@Argument Integer page, @Argument Integer size) {
		page = page != null ? page : 0;
		size = size != null ? size : 10;
		return puntoService.findByLatitudAndLongitudFP(latitud.doubleValue(), longitud.doubleValue(), page, size);
	}

}
