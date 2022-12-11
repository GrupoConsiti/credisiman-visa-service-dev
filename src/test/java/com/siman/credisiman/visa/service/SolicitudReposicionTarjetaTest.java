package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolicitudReposicionTarjetaTest {
    static private String NS = "http://siman.com/SolicitudReposicionTarjeta";

    @Test
    public void obtenerConsultaSubProductosOk() {
        XmlObject result = SolicitudReposicionTarjeta.crearSolicitudReposicionTarjeta("SLV", "4000123456780000","JUAN PEREZ","1", "jndi/SimacSV", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831","P");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
        //Data
        assertEquals("4000123456780001", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:nuevoNumeroTarjeta")[0]).getStringValue());
    }
}
