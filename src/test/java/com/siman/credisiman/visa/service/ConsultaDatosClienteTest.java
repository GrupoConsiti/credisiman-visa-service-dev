package com.siman.credisiman.visa.service;

import static org.junit.Assert.assertEquals;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

public class ConsultaDatosClienteTest {
	static private final String NS = "http://siman.com/ConsultaListadoTarjetas";

    @Test
    public void obtenerDatosClienteOk() {
        XmlObject result = ConsultaDatosCliente.obtenerDatosCliente("SLV", "0398765432", "jndi/SimacSV", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831");
        
        //Status
        assertEquals("00", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
        
        //Data
//        assertEquals("Juan", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:primerNombre")[0]).getStringValue());
//        assertEquals("Jos�", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:segundoNombre")[0]).getStringValue());
//        assertEquals("19810423", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:fechaNacimiento")[0]).getStringValue());
//        assertEquals("012345678", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:tipoIdentificacion")[0]).getStringValue());
//        assertEquals("77770000", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:celular")[0]).getStringValue());
    }
}
