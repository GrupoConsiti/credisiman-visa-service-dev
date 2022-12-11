package com.siman.credisiman.visa.dto.modificarlimitetarjeta;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModificarLimiteTarjetaRequest {
    public String pais;
    public String numeroTarjeta;
    public String monto;


}
