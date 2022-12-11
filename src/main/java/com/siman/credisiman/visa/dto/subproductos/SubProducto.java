package com.siman.credisiman.visa.dto.subproductos;

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
public class SubProducto {
    @JsonProperty("tipoMensaje") @JsonAlias("tipoMensaje")
    public String tipoMensaje;
    @JsonProperty("codigoEmisor") @JsonAlias("codigoEmisor")
    public String codigoEmisor;
    @JsonProperty("numeroCuenta") @JsonAlias("numeroCuenta")
    public String numeroCuenta;
    @JsonProperty("numeroTarjeta") @JsonAlias("numeroTarjeta")
    public String numeroTarjeta;
    @JsonProperty("fechaCreacion") @JsonAlias("fechaCreacion")
    public String fechaCreacion;
    @JsonProperty("numeroLineaSubProducto") @JsonAlias("numeroLineaSubProducto")
    public String numeroLineaSubProducto;
    @JsonProperty("codigoPlan") @JsonAlias("codigoPlan")
    public String codigoPlan;
    @JsonProperty("nombrePlan") @JsonAlias("nombrePlan")
    public String nombrePlan;
    @JsonProperty("tipoSubProducto") @JsonAlias("tipoSubProducto")
    public String tipoSubProducto;
    @JsonProperty("tasaInteres") @JsonAlias("tasaInteres")
    public String tasaInteres;
    @JsonProperty("codigoMoneda") @JsonAlias("codigoMoneda")
    public String codigoMoneda;
    @JsonProperty("montoInicial") @JsonAlias("montoInicial")
    public String montoInicial;
    @JsonProperty("saldoActual") @JsonAlias("saldoActual")
    public String saldoActual;
    @JsonProperty("montoCuotaActual") @JsonAlias("montoCuotaActual")
    public String montoCuotaActual;
    @JsonProperty("cuotasCobradas") @JsonAlias("cuotasCobradas")
    public String cuotasCobradas;
    @JsonProperty("plazo") @JsonAlias("plazo")
    public String plazo;
    @JsonProperty("fechaFinalizacion") @JsonAlias("fechaFinalizacion")
    public String fechaFinalizacion;
    @JsonProperty("porcentajeComisionDesembolso") @JsonAlias("porcentajeComisionDesembolso")
    public String porcentajeComisionDesembolso;
    @JsonProperty("porcentajeComisionPagoAnticipado") @JsonAlias("porcentajeComisionPagoAnticipado")
    public String porcentajeComisionPagoAnticipado;
    @JsonProperty("montoComisionPagoAnticipado") @JsonAlias("montoComisionPagoAnticipado")
    public String montoComisionPagoAnticipado;
}
