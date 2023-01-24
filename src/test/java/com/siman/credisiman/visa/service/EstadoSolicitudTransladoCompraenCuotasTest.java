package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EstadoSolicitudTransladoCompraenCuotasTest {
    private static final String NS = "http://siman.com/EstadoSolicitudTransladoCompraenCuotas";

    @Test
    public void obtenerConsultaMovimientosPrivadaGTOk() {
        XmlObject result = EstadoSolicitudTransladoCompraenCuotas.obtenerEstadoSolicitudTransladoCompraenCuotas("SV", "123456", "jdbc/SUNTSTGT", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }
}
