package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConsultaEstadoCuentaTest {
    static private final String NS = "http://siman.com/ConsultaEstadoCuenta";

    @Test
    public void obtenerDatosClienteVisaOk() {

        XmlObject result = ConsultaEstadoCuenta.obtenerConsultaEstadoCuenta("SV", "400012345507007",
                "12345","20221225","jndi/SimacSV", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario",
                "600831, 600831, 600831","V","https://api-crm.siman.com",
                "S4l3$force","$2a$10$lH7.4sT65MvGIkbr3buMpunEkmjc6fW735baDiwcvP8I7QJq3VUFe",
                "010161290");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerDatosClientePrivadaOk() {

        XmlObject result = ConsultaEstadoCuenta.obtenerConsultaEstadoCuenta("SV", "4573840083740702",
                "12345","20220709","jndi/SimacSV", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario",
                "600831, 600831, 600831","P","https://api-crm.siman.com",
                "S4l3$force","$2a$10$lH7.4sT65MvGIkbr3buMpunEkmjc6fW735baDiwcvP8I7QJq3VUFe",
                "010161290");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }
//    400012345507007
//    20220718

//    4573840083740702
//    20221225
}
