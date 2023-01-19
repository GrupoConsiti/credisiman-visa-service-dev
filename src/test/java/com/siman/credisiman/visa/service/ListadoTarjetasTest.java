package com.siman.credisiman.visa.service;

import static org.junit.Assert.assertEquals;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

public class ListadoTarjetasTest {
    static private final String NS = "http://siman.com/ListadoTarjetas";

    @Test
    public void obtenerDatosClienteOk() {
        XmlObject result = ListadoTarjetas.obtenerListadoTarjetas("SV", "333214569", "jdbc/SUNTST",
                "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831");
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerDatosClienteMultiplesOk() {
        XmlObject result = ListadoTarjetas.obtenerListadoTarjetas("SV", "015057232",
                "jdbc/SUNTST",
                "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831");
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    //015057232
    @Test
    public void obtenerDatosClienteOkVisaGT() {
        XmlObject result = ListadoTarjetas.obtenerListadoTarjetas("GT", "2452930431101",
                "jdbc/SUNTSTGT", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831");
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerDatosClientePrivadaOk() {
        XmlObject result = ListadoTarjetas.obtenerListadoTarjetas("SV", "048382810", "jdbc/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831");
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }
}