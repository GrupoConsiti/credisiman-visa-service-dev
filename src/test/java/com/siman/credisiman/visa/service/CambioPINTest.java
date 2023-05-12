package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CambioPINTest {
    static private final String NS = "http://siman.com/CambioPIN";

    @Test
    public void obtenerDatosClienteOk() {
        XmlObject result = CambioPIN.cambioPIN("SV", "4000123456780000", "xOCcB1WjksWWqbK/o8taUQ==","jdbc/SUNTST",
                "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "CDS00867", "600831, 600831, 600831",
                "V");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }
}
