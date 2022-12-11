package com.siman.credisiman.visa.dto.listadotarjeta;

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
public class ListadoTarjetasResponse {
    @JsonProperty("respuestas") @JsonAlias("respuestas")
    private Object respuestas;

    @JsonProperty("code") @JsonAlias("code")
    private  String code;

    @JsonProperty("message") @JsonAlias("message")
    private String message;

    @JsonProperty("statusCode") @JsonAlias("statusCode")
    public String statusCode;

    @JsonProperty("status") @JsonAlias("status")
    public String status;

    @JsonProperty("statusMessage") @JsonAlias("statusMessage")
    public String statusMessage;

    @JsonProperty("cedula") @JsonAlias("cedula")
    private String cedula;

    @JsonProperty("nombreTH") @JsonAlias("nombreTH")
    private String nombreTH;

    @JsonProperty("cuentas") @JsonAlias("cuentas")
    public List<CuentasResponse> cuentas;

}
