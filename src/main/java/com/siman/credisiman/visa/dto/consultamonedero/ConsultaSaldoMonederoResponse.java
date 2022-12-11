package com.siman.credisiman.visa.dto.consultamonedero;

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
public class ConsultaSaldoMonederoResponse {
    @JsonProperty("code") @JsonAlias("code")
    public String code;
    @JsonProperty("message") @JsonAlias("message")
    public String message;
    @JsonProperty("status") @JsonAlias("status")
    public String status;
    @JsonProperty("statusCode") @JsonAlias("statusCode")
    public String statusCode;
    @JsonProperty("statusMessage") @JsonAlias("statusMessage")
    public String statusMessage;
    @JsonProperty("saldoInicial") @JsonAlias("saldoInicial")
    public String saldoInicial;
    @JsonProperty("puntosGanados") @JsonAlias("puntosGanados")
    public String puntosGanados;
    @JsonProperty("puntosCanjeados") @JsonAlias("puntosCanjeados")
    public String puntosCanjeados;
    @JsonProperty("saldoFinal") @JsonAlias("saldoFinal")
    public String saldoFinal;

    @JsonProperty("respuestas") @JsonAlias("respuestas")
    private Object respuestas;
}
