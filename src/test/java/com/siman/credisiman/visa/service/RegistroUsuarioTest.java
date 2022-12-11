package com.siman.credisiman.visa.service;

import static org.junit.Assert.assertEquals;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

public class RegistroUsuarioTest {
    static private final String NS = "http://siman.com/RegistroUsuario";

    @Test
    public void registroUsuario() {
        XmlObject result = RegistroUsuario.registroUsuario("SV", "Juan", null, "Peréz", null, null, "SV", "20000907", "DUI", "001000105", "juan.perez@gmail.com", "77776666", "juan.perez@gmail.com", "60607070", "jndi/SimacSV", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831");
        assertEquals("00", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void actualizarUsuario() {
        XmlObject result = RegistroUsuario.actualizarUsuario(null, null, null, null, null, null, null, null, null, null, null, null, null, null, "jndi/SimacSV", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831");
        assertEquals("00", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }
}
