package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BloqueoDesbloqueoTarjetaTest {
    static private final String NS = "http://siman.com/BloqueoDesbloqueoTarjeta";

    @Test
    public void bloquearTarjetaTemporalOkSV() {
        XmlObject result = BloqueoDesbloqueoTarjeta.obtenerBloqueoDesbloqueoTarjeta("SV", "6008315500062519",
                "1", "M6", "jdbc/SUNTST", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831",
                "P");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());

    }

    @Test
    public void desbloquearTarjetaTemporalOkSV() {
        XmlObject result = BloqueoDesbloqueoTarjeta.obtenerBloqueoDesbloqueoTarjeta("SV", "6008315500062519",
                "0", "M6", "jdbc/SUNTST", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831",
                "P");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());

    }

    @Test
    public void bloquearTarjetaTemporalOkGT() {
        XmlObject result = BloqueoDesbloqueoTarjeta.obtenerBloqueoDesbloqueoTarjeta("GT", "6008320201460400",
                "1", "M6", "jdbc/SUNTSTGT", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831",
                "P");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());

    }

    @Test
    public void desbloquearTarjetaTemporalOkGT() {
        XmlObject result = BloqueoDesbloqueoTarjeta.obtenerBloqueoDesbloqueoTarjeta("GT", "6008320201460400",
                "0", "M6", "jdbc/SUNTSTGT", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831",
                "P");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());

    }

    @Test
    public void bloquearTarjetaTemporalOkNI() {
        XmlObject result = BloqueoDesbloqueoTarjeta.obtenerBloqueoDesbloqueoTarjeta("NI", "6275815100001870",
                "1", "M6", "jdbc/SUNTSTNI", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831",
                "P");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());

    }

    @Test
    public void desbloquearTarjetaTemporalOkNI() {
        XmlObject result = BloqueoDesbloqueoTarjeta.obtenerBloqueoDesbloqueoTarjeta("NI", "6275815100001870",
                "0", "M6", "jdbc/SUNTSTNI", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831",
                "P");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());

    }

    @Test
    public void bloquearTarjetaTemporalOkCR() {
        XmlObject result = BloqueoDesbloqueoTarjeta.obtenerBloqueoDesbloqueoTarjeta("CR", "6275804000061457",
                "1", "M6", "jdbc/SUNTSTCR", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831",
                "P");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());

    }

    @Test
    public void desbloquearTarjetaTemporalOkCR() {
        XmlObject result = BloqueoDesbloqueoTarjeta.obtenerBloqueoDesbloqueoTarjeta("CR", "6275804000061457",
                "0", "M6", "jdbc/SUNTSTCR", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831",
                "P");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());

    }
}
