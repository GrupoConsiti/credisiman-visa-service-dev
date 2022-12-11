package com.siman.credisiman.visa.dto.consultamovimientos;

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
public class ConsultaMovimientosResponse {

	@JsonProperty("code") @JsonAlias("code")
	public String code;

	@JsonProperty("message") @JsonAlias("message")
	public String message;

	@JsonProperty("statusCode") @JsonAlias("statusCode")
    public String statusCode;

    @JsonProperty("status") @JsonAlias("status")
    public String status;

    @JsonProperty("statusMessage") @JsonAlias("statusMessage")
    public String statusMessage;
    
    @JsonProperty("movimientos") @JsonAlias("movimientos")
    public List<MovimientosResponse> movimientos;

}
