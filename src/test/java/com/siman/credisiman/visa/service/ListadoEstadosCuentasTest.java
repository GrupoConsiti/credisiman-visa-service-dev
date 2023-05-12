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
                "jndi/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "V",
                "010161290", "", "ORIONREPOSV", "", "");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerListadoEstadosCuentasPrivadaOk() {
        XmlObject result = ListadoEstadosCuentas.obtenerListadoEstadosCuenta("NI", "6275814000064401",
                "jndi/SUNTST", "jdbc/ORIONREPONI", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "P",
                "0012201890026R", "", "ORIONREPONI", "", "");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerListadoEstadosCuentasPrivadaEstctaOk() {
        XmlObject result = ListadoEstadosCuentas.obtenerListadoEstadosCuenta("SV", "702",
                "jndi/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "P",
                "65163", "", "", "ESTCTA", "");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    // LISTADOS ESTADO CUENTA

    @Test
    public void obtenerListadoEstadosCuentasPrivadaEstctaOk2() {
        XmlObject result = ListadoEstadosCuentas.obtenerListadoEstadosCuenta("SV", "702",
                "jndi/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "P",
                "65163", "", "", "ESTCTASV", "http://192.168.151.205:3020/credisimanRESTWS/cards/estados/");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerListadoEstadosCuentasVisaEstctaOk2() {
        XmlObject result = ListadoEstadosCuentas.obtenerListadoEstadosCuenta("SV", "507007",
                "jndi/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "V",
                "010161290", "", "ORIONREPOSV", "ESTCTASV", "");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerListadoEstadosCuentasPrivadaServiceOk() {
        XmlObject result = ListadoEstadosCuentas.obtenerListadoEstadosCuenta("SV", "6008315100183194",
                "jndi/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "P",
                "", "", "", "ESTCTASV",
                "http://192.168.151.205:3020/credisimanRESTWS/cards/estados/");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    // FAIL TEST
    @Test
    public void obtenerListadoEstadosCuentasPrivadaTest() {
        XmlObject result = ListadoEstadosCuentas.obtenerListadoEstadosCuenta("SV", "0000000000",
                "jndi/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831", "V",
                "0000000000", "", "ORIONREPOSV", "ESTCTASV",
                "http://192.168.151.205:3020/credisimanRESTWS/cards/estados/");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }
}