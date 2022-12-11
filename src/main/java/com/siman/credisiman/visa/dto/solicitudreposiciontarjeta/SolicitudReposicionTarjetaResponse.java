package com.siman.credisiman.visa.dto.solicitudreposiciontarjeta;

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
public class SolicitudReposicionTarjetaResponse {
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
    @JsonProperty("numeroTarjeta") @JsonAlias("numeroTarjeta")
    public String numeroTarjeta;
    @JsonProperty("nuevoNumeroTarjeta") @JsonAlias("nuevoNumeroTarjeta")
    public String nuevoNumeroTarjeta;
}
