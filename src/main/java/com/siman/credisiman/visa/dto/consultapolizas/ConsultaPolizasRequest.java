package com.siman.credisiman.visa.dto.consultapolizas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ConsultaPolizasRequest {
    public String pais;
    public String numeroTarjeta;

}
