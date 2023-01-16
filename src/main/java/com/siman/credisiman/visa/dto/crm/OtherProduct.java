package com.siman.credisiman.visa.dto.crm;


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
public class OtherProduct {
    @JsonProperty("tipoDePoliza") @JsonAlias("tipoDePoliza")
    private String tipoDePoliza;
    @JsonProperty("nombreDePoliza") @JsonAlias("nombreDePoliza")
    private String nombreDePoliza;
    @JsonProperty("precio") @JsonAlias("precio")
    private String precio;
    @JsonProperty("moneda") @JsonAlias("moneda")
    private String moneda;
    @JsonProperty("estado") @JsonAlias("estado")
    private String estado;
    @JsonProperty("fechaDeInclusion") @JsonAlias("fechaDeInclusion")
    private String fechaDeInclusion;
}
