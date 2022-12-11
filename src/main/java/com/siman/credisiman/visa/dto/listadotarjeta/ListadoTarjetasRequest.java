package com.siman.credisiman.visa.dto.listadotarjeta;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListadoTarjetasRequest {
    public String pais;
    public String identificacion;
}
