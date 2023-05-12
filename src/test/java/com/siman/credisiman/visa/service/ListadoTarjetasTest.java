package com.siman.credisiman.visa.service;

import static org.junit.Assert.assertEquals;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

public class ListadoTarjetasTest {
    static private final String NS = "http://siman.com/ListadoTarjetas";

    @Test
    public void obtenerDatosClienteOk() {
        //No trae datos
        XmlObject result = ListadoTarjetas.obtenerListadoTarjetas("SV", "333214569", "jdbc/SUNTST",
                "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "SUNNELP3", "", "", "SIMANAPP",
                "VlRCc1RsRlZOVUpWUmtKWVZYcEZlZz09", "192.168.23.62", "1", "01",
                "http://soauat.siman.com:7003/clubEventos");
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerDatosClienteMultiplesOk() {
        XmlObject result = ListadoTarjetas.obtenerListadoTarjetas("SV", "015057232",
                "jdbc/SUNTST",
                "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "SUNNELP3", "", "", "SIMANAPP",
                "VlRCc1RsRlZOVUpWUmtKWVZYcEZlZz09", "192.168.23.62", "1", "01",
                "http://soauat.siman.com:7003/clubEventos");
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    //015057232
    @Test
    public void obtenerDatosClienteOkVisaGT() {
        XmlObject result = ListadoTarjetas.obtenerListadoTarjetas("GT", "3731030260101",
                "jdbc/SUNTSTGT", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831",
                "SUNNELGTP4", "", "", "SIMANAPP",
                "VlRCc1RsRlZOVUpWUmtKWVZYcEZlZz09", "192.168.23.62", "1", "01",
                "http://soauat.siman.com:7003/clubEventos");
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerDatosClienteOkVisaSV() {
        XmlObject result = ListadoTarjetas.obtenerListadoTarjetas("SV", "045810678",
                "jdbc/SUNTST", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831",
                "SUNNELP3", "", "", "SIMANAPP",
                "VlRCc1RsRlZOVUpWUmtKWVZYcEZlZz09", "192.168.23.62", "1", "01",
                "http://soauat.siman.com:7003/clubEventos");
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerDatosClientePrivadaOk() {
        XmlObject result = ListadoTarjetas.obtenerListadoTarjetas("SV", "048382810", "jdbc/SUNTST", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831", "SUNNELP3",
                "", "", "SIMANAPP",
                "VlRCc1RsRlZOVUpWUmtKWVZYcEZlZz09", "192.168.23.62", "1", "01",
                "http://soauat.siman.com:7003/clubEventos");
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerDatosClienteOkVisaCR() {
        XmlObject result = ListadoTarjetas.obtenerListadoTarjetas("CR", "5 0225 0153", "jdbc/SUNTSTCR", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831", "SUNNELCRP4",
                "", "", "SIMANAPP",
                "VlRCc1RsRlZOVUpWUmtKWVZYcEZlZz09", "192.168.23.62", "1", "01",
                "http://soauat.siman.com:7003/clubEventos");
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }
}