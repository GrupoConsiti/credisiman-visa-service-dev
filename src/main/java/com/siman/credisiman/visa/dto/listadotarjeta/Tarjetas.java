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
public class Tarjetas {

    @JsonProperty("numeroTarjeta")
    @JsonAlias("numeroTarjeta")
    public String numeroTarjeta;

    @JsonProperty("cuenta")
    @JsonAlias("cuenta")
    public String cuenta;

    @JsonProperty("tipoTarjeta")
    @JsonAlias("tipoTarjeta")
    public String tipoTarjeta;

    @JsonProperty("nombreTH")
    @JsonAlias("nombreTH")
    public String nombreTH;

    @JsonProperty("estado")
    @JsonAlias("estado")
    public String estado;

    @JsonProperty("limiteCreditoLocal")
    @JsonAlias("limiteCreditoLocal")
    public String limiteCreditoLocal;

    @JsonProperty("limiteCreditoDolares")
    @JsonAlias("limiteCreditoDolares")
    public String limiteCreditoDolares;

    @JsonProperty("saldoLocal")
    @JsonAlias("saldoLocal")
    public String saldoLocal;

    @JsonProperty("saldoDolares")
    @JsonAlias("saldoDolares")
    public String saldoDolares;

    @JsonProperty("disponibleLocal")
    @JsonAlias("disponibleLocal")
    public String disponibleLocal;

    @JsonProperty("disponibleDolares")
    @JsonAlias("disponibleDolares")
    public String disponibleDolares;

    @JsonProperty("pagoMinimoLocal")
    @JsonAlias("pagoMinimoLocal")
    public String pagoMinimoLocal;

    @JsonProperty("pagoMinimoDolares")
    @JsonAlias("pagoMinimoDolares")
    public String pagoMinimoDolares;

    @JsonProperty("pagoMinimoVencidoLocal")
    @JsonAlias("pagoMinimoVencidoLocal")
    public String pagoMinimoVencidoLocal;

    @JsonProperty("pagoMinimoVencidoDolares")
    @JsonAlias("pagoMinimoVencidoDolares")
    public String pagoMinimoVencidoDolares;

    @JsonProperty("pagoContadoLocal")
    @JsonAlias("pagoContadoLocal")
    public String pagoContadoLocal;

    @JsonProperty("pagoContadoDolares")
    @JsonAlias("pagoContadoDolares")
    public String pagoContadoDolares;

    @JsonProperty("fechaPago")
    @JsonAlias("fechaPago")
    public String fechaPago;

    @JsonProperty("fechaUltimoCorte")
    @JsonAlias("fechaUltimoCorte")
    public String fechaUltimoCorte;

    @JsonProperty("saldoMonedero")
    @JsonAlias("saldoMonedero")
    public String saldoMonedero;

    @JsonProperty("rombosAcumulados")
    @JsonAlias("rombosAcumulados")
    public String rombosAcumulados;

    @JsonProperty("rombosDinero")
    @JsonAlias("rombosDinero")
    public String rombosDinero;

    @JsonProperty("fondosReservadosLocal")
    @JsonAlias("fondosReservadosLocal")
    public String fondosReservadosLocal;

    @JsonProperty("fondosReservadosDolares")
    @JsonAlias("fondosReservadosDolares")
    public String fondosReservadosDolares;
}
