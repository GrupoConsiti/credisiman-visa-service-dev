package com.siman.credisiman.visa.service;

import static org.junit.Assert.assertEquals;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

public class ConsultaDatosClienteTest {
    static private final String NS = "http://siman.com/ConsultaDatosCliente";

    @Test
    public void obtenerDatosClienteOk() {
        XmlObject result = ConsultaDatosCliente.obtenerDatosCliente("SV", "048382810", "jdbc/SUNTST",
                "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "CDS00867",
                "600831, 600831, 600831", "sunnelp3","","");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerDatosClienteGTOk() {
        XmlObject result = ConsultaDatosCliente.obtenerDatosCliente("GT", "1689133130101",
                "jdbc/SUNTSTGT", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "SUNNELGTP4","","");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());

    }

    @Test
    public void obtenerDatosClienteEvertecOk() {
        XmlObject result = ConsultaDatosCliente.obtenerDatosCliente("SV", "004653253", "jdbc/SUNTST",
                "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "CDS00867",
                "600831, 600831, 600831", "","","");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerDatosClienteNIOk() {
        XmlObject result = ConsultaDatosCliente.obtenerDatosCliente("NI", "001 2509650 021A",
                "jdbc/SUNTSTNI", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "SUNNELNIP1","","");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());

    }

    @Test
    public void obtenerDatosClienteCROk() {
        XmlObject result = ConsultaDatosCliente.obtenerDatosCliente("CR", "203260954",
                "jdbc/SUNTSTCR", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "SUNNELCRP4","","");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());

    }

}
