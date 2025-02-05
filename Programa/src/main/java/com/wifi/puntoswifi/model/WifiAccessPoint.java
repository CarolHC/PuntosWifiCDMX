package com.wifi.puntoswifi.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true) // Evita problemas con valores no inicializados
@Entity
@Table(name = "PUNTO")
@Schema(description = "Modelo que representa los parámetros de los puntos WiFi")
public class WifiAccessPoint{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(nullable = false, description = "Incremental, llave subrogada")
    private Long dbid;

    @Column(name = "id", length = 256)
    @Schema(description = "ID proveniente de la data raw")
    private String id;

    @Column(name = "programa", length = 256)
    @Schema(description = "Programa social sobre el cual se generó el servicio de instalación")
    private String programa;

    @Column(name = "fecha_instalacion")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(description = "Fecha de instalación")
    private LocalDate fecha_instalacion;

    @Column(name = "latitud")
    @Schema(description = "Latitud geográfica del punto WiFi")
    private Double latitud;

    @Column(name = "longitud")
    @Schema(description = "Longitud geográfica del punto WiFi")
    private Double longitud;

    @Column(name = "colonia", length = 256)
    @Schema(description = "Colonia donde se encuentra el punto WiFi")
    private String colonia;

    @Column(name = "alcaldia", length = 256)
    @Schema(description = "Alcaldía responsable del punto WiFi")
    private String alcaldia;
}
