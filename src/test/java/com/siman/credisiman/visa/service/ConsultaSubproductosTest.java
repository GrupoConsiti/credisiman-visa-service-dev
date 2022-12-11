package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConsultaSubproductosTest {
    static private String NS = "http://siman.com/ConsultaSubproductos";
    static String[] listadoTarjetas = {"20220718", "20220717", "20220716"};

    @Test
    public void obtenerConsultaSubProductosOk() {
        XmlObject result = ConsultaSubproductos.obtenerConsultaSubproductos("SLV", "0398765432", "jndi/SimacSV", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831","P");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }
}
