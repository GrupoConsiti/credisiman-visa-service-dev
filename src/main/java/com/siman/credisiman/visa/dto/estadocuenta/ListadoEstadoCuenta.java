package com.siman.credisiman.visa.dto.estadocuenta;

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
public class ListadoEstadoCuenta {
    @JsonProperty("origen")
    @JsonAlias("origen")
    private String origen;

    @JsonProperty("fechaCorte")
    @JsonAlias("fechaCorte")
    private String fechaCorte;

    @JsonProperty("nombreArchivo")
    @JsonAlias("nombreArchivo")
    private String nombreArchivo;

    @JsonProperty("ruta")
    @JsonAlias("ruta")
    private String ruta;
}
