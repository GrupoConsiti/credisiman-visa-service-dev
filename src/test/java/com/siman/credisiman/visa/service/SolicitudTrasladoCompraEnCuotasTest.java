package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolicitudTrasladoCompraEnCuotasTest {
    private static final String NS = "http://siman.com/ListarPlanesPorProducto";

    @Test
    public void obtenerConsultaMovimientosPrivadaGTOk() {
        XmlObject result = ListarPlanesPorProducto.obtenerListarPlanesPorProducto("SV",
                "01", "jdbc/SUNTSTGT", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }
}
