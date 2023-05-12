package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ListadoProductosTest {
    private static final String NS = "http://siman.com/ListadoProductos";

    @Test
    public void obtenerListadoProductosSVOk() {
        XmlObject result = ListadoProductos.obtenerListadoProductos("SV", "jdbc/SUNTST",
                "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "", "ORIONREPOSV", "");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerListadoProductosGTOk() {
        XmlObject result = ListadoProductos.obtenerListadoProductos("GT", "jdbc/SUNTST",
                "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "", "ORIONREPOGT", "");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerListadoProductosNIOk() {
        XmlObject result = ListadoProductos.obtenerListadoProductos("NI", "jdbc/SUNTST",
                "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "", "ORIONREPONI", "");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerListadoProductosCROk() {
        XmlObject result = ListadoProductos.obtenerListadoProductos("CR", "jdbc/SUNTST",
                "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "", "ORIONREPOCR", "");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerListadoProductosNoExiste() {
        XmlObject result = ListadoProductos.obtenerListadoProductos("PAIS", "jdbc/SUNTST",
                "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "", "ORIONREPOCR", "");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }
}
