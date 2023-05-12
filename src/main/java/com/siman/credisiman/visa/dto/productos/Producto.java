package com.siman.credisiman.visa.dto.productos;

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
public class Producto {
    @JsonProperty("codigoProducto")
    @JsonAlias("codigoProducto")
    private String codigoProducto;

    @JsonProperty("descripcion")
    @JsonAlias("descripcion")
    private String descripcion;
}
