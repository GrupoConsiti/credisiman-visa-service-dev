package com.siman.credisiman.visa.dto.cambiopin;

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
public class Respuesta {
    @JsonProperty("status") @JsonAlias("status")
    private String status;
    @JsonProperty("statusCode") @JsonAlias("statusCode")
    private String statusCode;
    @JsonProperty("statusMessage") @JsonAlias("statusMessage")
    private String statusMessage;
}
