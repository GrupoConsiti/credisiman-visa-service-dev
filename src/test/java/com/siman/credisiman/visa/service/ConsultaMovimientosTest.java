package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConsultaMovimientosTest {
    static private final String NS = "http://siman.com/ConsultaMovimientos";

    @Test
    public void obtenerConsultaMovimientosPrivadaOk() {
        XmlObject result = ConsultaMovimientos.obtenerConsultaMovimientos("SV",
                "6008314502274917", "20180101", "20221231",
                "jdbc/SimacSV", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "P", "SUNNELP3","","");
//        int i = 0;
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerConsultaMovimientosPrivadaGTOk() {
        XmlObject result = ConsultaMovimientos.obtenerConsultaMovimientos("GT",
                "6008324000363591", "20180101", "20221231",
                "jdbc/SUNTSTGT", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "P", "SUNNELGTP4","","");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerConsultaMovimientosVisaOk() {
        XmlObject result = ConsultaMovimientos.obtenerConsultaMovimientos("SV",
                "4573840020133086", "20190916", "20211030",
                "jdbc/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "V", "SUNNELP3","","");
//        int i = 0;
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerConsultaMovimientosPrivadaNIOk() {
        XmlObject result = ConsultaMovimientos.obtenerConsultaMovimientos("NI",
                "6275811600007806", "20180101", "20231030",
                "jdbc/SUNTSTNI", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "P", "SUNNELNIP1","","");
//        int i = 0;
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerConsultaMovimientosPrivadaCROk() {
        XmlObject result = ConsultaMovimientos.obtenerConsultaMovimientos("CR",
                "6275804000095489", "20180101", "20221231",
                "jdbc/SimacCR", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "P", "SUNNELCRP4","","");
//        int i = 0;
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerConsultaMovimientosPrivadaCROk2() {
        XmlObject result = ConsultaMovimientos.obtenerConsultaMovimientos("CR",
                "6275804000587964", "20180101", "20221231",
                "jdbc/SimacCR", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "P", "SUNNELCRP4","","");
//        int i = 0;
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }
}
