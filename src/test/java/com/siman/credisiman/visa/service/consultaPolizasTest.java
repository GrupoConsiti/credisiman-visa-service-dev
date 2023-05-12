package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class consultaPolizasTest {
    static private final String NS = "http://siman.com/ConsultaPolizas";

    @Test
    public void obtenerConsultaPolizasVisaOk() {
        XmlObject result = ConsultaPolizas.obtenerConsultaPolizas("SV", "4573840094950811",
                "jdbc/SimacSV", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "V", "SUNNELP3","","");
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerConsultaPolizasPrivadaGTOk() {
        XmlObject result = ConsultaPolizas.obtenerConsultaPolizas("GT", "6008324000363591",
                "jdbc/SUNTSTGT", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "P", "SUNNELGTP4","","");
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerConsultaPolizasPrivadaOk() {
        XmlObject result = ConsultaPolizas.obtenerConsultaPolizas("SV", "6008310100104100",
                "jdbc/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "P", "SUNNELP3","","");
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerConsultaPolizasVisaCROk() {
        XmlObject result = ConsultaPolizas.obtenerConsultaPolizas("CR", "6275804000051482",
                "jdbc/SUNTSTCR", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "P", "SUNNELCRP4","","");
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }
}
