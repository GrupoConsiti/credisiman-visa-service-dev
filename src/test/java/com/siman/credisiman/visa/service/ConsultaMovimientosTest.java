package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConsultaMovimientosTest {
    static private String NS = "http://siman.com/ConsultaMovimientos";
    static String[] ListadoNumeroAutorizacion  = {  "123456703","123456702","123456701"};

    @Test
    public void obtenerConsultaMovimientosOk() {

        XmlObject result = ConsultaMovimientos.obtenerConsultaMovimientos("SLV",
                "4000123456780000", "20220701", "20220731",
                "jndi/SimacSV", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831","P");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());

    }
}
