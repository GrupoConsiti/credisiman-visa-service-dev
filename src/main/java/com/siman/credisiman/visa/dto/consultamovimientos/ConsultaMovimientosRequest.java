package com.siman.credisiman.visa.dto.consultamovimientos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConsultaMovimientosRequest {
    public String pais;
    public String numeroTarjeta;
    public String fechaInicial;
    public String fechaFinal;
}
