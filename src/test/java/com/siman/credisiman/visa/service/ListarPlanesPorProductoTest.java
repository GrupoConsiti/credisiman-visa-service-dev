package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ListarPlanesPorProductoTest {
    private static final String NS = "http://siman.com/SolicitudTrasladoCompraEnCuotas";

    @Test
    public void obtenerConsultaMovimientosPrivadaGTOk() {
        XmlObject result = SolicitudTrasladoCompraEnCuotas.obtenerSolicitudTrasladoCompraEnCuotas("SV","123456",
                "6008324000363591", "carlos_penate@siman.com", "22228888","01","89","18.2",
                "20220117","02", "jdbc/SUNTSTGT", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }
}
