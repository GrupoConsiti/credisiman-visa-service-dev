package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConsultaSubproductosTest {
    static private final String NS = "http://siman.com/ConsultaSubproductos";

    @Test
    public void obtenerConsultaSubProductosOk() {
        XmlObject result = ConsultaSubproductos.obtenerConsultaSubproductos("SV", "4573840079870646",
                "jdbc/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831","V","https://api-crm.siman.com",
                "S4l3$force","$2a$10$lH7.4sT65MvGIkbr3buMpunEkmjc6fW735baDiwcvP8I7QJq3VUFe");
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerConsultaSubProductosPrivadaOk() {
        XmlObject result = ConsultaSubproductos.obtenerConsultaSubproductos("SV", "4573840079870646",
                "jdbc/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831","P","https://api-crm.siman.com",
                "S4l3$force","$2a$10$lH7.4sT65MvGIkbr3buMpunEkmjc6fW735baDiwcvP8I7QJq3VUFe");
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }
}
