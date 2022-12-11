package com.siman.credisiman.visa.dto.listadotarjeta;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TarjetasResponse {
    @JsonProperty("estadoTarjeta")
    @JsonAlias("estadoTarjeta")
    private String estadoTarjeta;

    @JsonProperty("limiteCreditoLocal")
    @JsonAlias("limiteCreditoLocal")
    private String limiteCreditoLocal;

    @JsonProperty("limiteCreditoInter")
    @JsonAlias("limiteCreditoInter")
    private String limiteCreditoInter;

    @JsonProperty("numeroTarjeta")
    @JsonAlias("numeroTarjeta")
    private String numeroTarjeta;

    @JsonProperty("dispIntTarjeta")
    @JsonAlias("dispIntTarjeta")
    private String dispIntTarjeta;

    @JsonProperty("dispLocalTarjeta")
    @JsonAlias("dispLocalTarjeta")
    private String dispLocalTarjeta;

    @JsonProperty("tipoTarjeta")
    @JsonAlias("tipoTarjeta")
    private String tipoTarjeta;

    @JsonProperty("nombreTH")
    @JsonAlias("nombreTH")
    private String nombreTH;
}
