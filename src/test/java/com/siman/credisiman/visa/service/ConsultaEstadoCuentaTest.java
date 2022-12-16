package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConsultaEstadoCuentaTest {
    static private String NS = "http://siman.com/ConsultaEstadoCuenta";

    @Test
    public void obtenerDatosClienteOk() {
        XmlObject result = ConsultaEstadoCuenta.obtenerConsultaEstadoCuenta("SLV", "4000123456780000", "12345","20220718","jndi/SimacSV", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831","P","");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
        assertEquals("juan.perez@correo.com", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:correo")[0]).getStringValue());
        assertEquals("StringBase24", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:archivo")[0]).getStringValue());

    }
}
