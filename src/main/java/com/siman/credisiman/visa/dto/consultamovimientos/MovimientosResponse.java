package com.siman.credisiman.visa.dto.consultamovimientos;

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
public class MovimientosResponse {

    @JsonProperty("tipoMovimiento")
    @JsonAlias("tipoMovimiento")
    private String tipoMovimiento;

    @JsonProperty("fechaMovimiento")
    @JsonAlias("fechaMovimiento")
    private String fechaMovimiento;

    @JsonProperty("fechaConsumo")
    @JsonAlias("fechaConsumo")
    private String fechaConsumo;

    @JsonProperty("codigoMoneda")
    @JsonAlias("codigoMoneda")
    private String codigoMoneda;

    @JsonProperty("monto")
    @JsonAlias("monto")
    private String monto;

    @JsonProperty("numeroAutorizacion")
    @JsonAlias("numeroAutorizacion")
    private String numeroAutorizacion;

    @JsonProperty("descripcionComercio")
    @JsonAlias("descripcionComercio")
    private String descripcionComercio;

    @JsonProperty("tipoMovimientoTH")
    @JsonAlias("tipoMovimientoTH")
    private String tipoMovimientoTH;

    //others

    @JsonProperty("numeroDocumento1")
    @JsonAlias("numeroDocumento1")
    private String numeroDocumento1;

    @JsonProperty("tipo")
    @JsonAlias("tipo")
    private String tipo;

    @JsonProperty("numeroTarjeta")
    @JsonAlias("numeroTarjeta")
    private String numeroTarjeta;

    @JsonProperty("codigoMCC")
    @JsonAlias("codigoMCC")
    private String codigoMCC;

    @JsonProperty("descripcionMCC")
    @JsonAlias("descripcionMCC")
    private String descripcionMCC;

    @JsonProperty("categoriaMCC")
    @JsonAlias("categoriaMCC")
    private String categoriaMCC;
}
