package com.wifi.puntoswifi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Schema(description = "Pojo para Modelo que representa la información de los puntos WIFI")
public class PuntoDistanciaDTO
{
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Icremental, Llave Subrogada")
    private WifiAccessPoint punto;

    @Schema(
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            description = "Proporciona la localización de un lugar, corren de norte a sur y marcan la posicion este-oeste de un meridiano")
    private Double distancia;
}
