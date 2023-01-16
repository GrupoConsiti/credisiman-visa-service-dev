package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ListadoEstadosCuentasTest {
    private static final String NS = "http://siman.com/ListadoEstadosCuenta";

    @Test
    public void obtenerListadoEstadosCuentasOk() {
        XmlObject result = ListadoEstadosCuentas.obtenerListadoEstadosCuenta("SV", "400012345507007",
                "jndi/SimacSV", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831","V","https://api-crm.siman.com",
                "S4l3$force","$2a$10$lH7.4sT65MvGIkbr3buMpunEkmjc6fW735baDiwcvP8I7QJq3VUFe",
                "010161290");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath( "declare namespace ns='" +NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

}