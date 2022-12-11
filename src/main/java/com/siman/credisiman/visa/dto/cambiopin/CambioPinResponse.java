package com.siman.credisiman.visa.dto.cambiopin;

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
public class CambioPinResponse {
    @JsonProperty("code") @JsonAlias("code")
    private String code;

    @JsonProperty("message") @JsonAlias("message")
    private String message;

    @JsonProperty("statusCode") @JsonAlias("statusCode")
    private String statusCode;

    @JsonProperty("status") @JsonAlias("status")
    private String status;

    @JsonProperty("statusMessage") @JsonAlias("statusMessage")
    private String statusMessage;

    @JsonProperty("respuestas") @JsonAlias("respuestas")
    private List<Respuesta> respuestas;
}
