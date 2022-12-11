package com.siman.credisiman.visa.dto.subproductos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultaSubproductosResponse {
    @JsonProperty("code")  @JsonAlias("code")
    private String code;
    @JsonProperty("message") @JsonAlias("message")
    public String message;
    @JsonProperty("status") @JsonAlias("status")
    public String status;
    @JsonProperty("statusCode") @JsonAlias("statusCode")
    public String statusCode;
    @JsonProperty("statusMessage") @JsonAlias("statusMessage")
    public String statusMessage;
    @JsonProperty("tipoMensaje") @JsonAlias("tipoMensaje")
    public String tipoMensaje;
    @JsonProperty("codigoEmisor") @JsonAlias("codigoEmisor")
    public String codigoEmisor;
    @JsonProperty("numeroCuenta") @JsonAlias("numeroCuenta")
    public String numeroCuenta;
    @JsonProperty("numeroTarjeta") @JsonAlias("numeroTarjeta")
    public String numeroTarjeta;
    @JsonProperty("usuarioSiscard") @JsonAlias("usuarioSiscard")
    public String usuarioSiscard;
    @JsonProperty("saldoDeudorExtrafinanciamientoLocal") @JsonAlias("saldoDeudorExtrafinanciamientoLocal")
    public String saldoDeudorExtrafinanciamientoLocal;
    @JsonProperty("saldoDeudorExtrafinanciamientoInter") @JsonAlias("saldoDeudorExtrafinanciamientoInter")
    public String saldoDeudorExtrafinanciamientoInter;
    @JsonProperty("saldoDeudorIntrafinanciamientoLocal") @JsonAlias("saldoDeudorIntrafinanciamientoLocal")
    public String saldoDeudorIntrafinanciamientoLocal;
    @JsonProperty("saldoDeudorIntrafinanciamientoInter") @JsonAlias("saldoDeudorIntrafinanciamientoInter")
    public String saldoDeudorIntrafinanciamientoInter;
    @JsonProperty("saldoDisponible") @JsonAlias("saldoDisponible")
    public String saldoDisponible;
    @JsonProperty("subproductos") @JsonAlias("subproductos")
    public List<SubProducto> subproductos;

}
