package com.siman.credisiman.visa.dto.consultapolizas;

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
public class ListaDePolizasResponse {

    @JsonProperty("tipoMensaje")
    @JsonAlias("tipoMensaje")
    private String tipoMensaje;

    @JsonProperty("numeroTarjeta")
    @JsonAlias("numeroTarjeta")
    private String numeroTarjeta;

    @JsonProperty("tipoPoliza")
    @JsonAlias("tipoPoliza")
    private String tipoPoliza;

    @JsonProperty("nombrePoliza")
    @JsonAlias("nombrePoliza")
    private String nombrePoliza;

    @JsonProperty("estadoPoliza")
    @JsonAlias("estadoPoliza")
    private String estadoPoliza;

    @JsonProperty("fechaInclusion")
    @JsonAlias("fechaInclusion")
    private String fechaInclusion;

    @JsonProperty("fechaUltimaAplicacion")
    @JsonAlias("fechaUltimaAplicacion")
    private String fechaUltimaAplicacion;

    @JsonProperty("usuarioPoliza")
    @JsonAlias("usuarioPoliza")
    private String usuarioPoliza;

    @JsonProperty("recargaPorInclusionDePoliza")
    @JsonAlias("recargaPorInclusionDePoliza")
    private String recargaPorInclusionDePoliza;

    @JsonProperty("recargaPorFechaDePoliza")
    @JsonAlias("recargaPorFechaDePoliza")
    private String recargaPorFechaDePoliza;
}
