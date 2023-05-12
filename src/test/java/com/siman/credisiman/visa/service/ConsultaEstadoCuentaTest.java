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
                "20221225", "jndi/SimacSV", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario",
                "600831, 600831, 600831", "V",
                "010161290", "", "ORIONREPOSV", "", "", "", "");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerDatosClientePrivadaOk() {

        XmlObject result = ConsultaEstadoCuenta.obtenerConsultaEstadoCuenta("SV", "702",
                "20230120", "jndi/SimacSV", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario",
                "600831, 600831, 600831", "P",
                "65163", "", "", "ESTCTASV", "", "", "");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }
//    400012345507007
//    20220718

//    4573840083740702
//    20221225

    @Test
    public void obtenerDatosClientePrivada2Ok() {

        XmlObject result = ConsultaEstadoCuenta.obtenerConsultaEstadoCuenta("SV", "974005",
                "20230106", "jndi/SimacSV", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario",
                "600831, 600831, 600831", "P",
                "21214", "", "", "ESTCTASV", "", "", "");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    // CONSULTA ESTADOS CUENTA

    @Test
    public void obtenerDatosClientePrivadaOk2() {

        XmlObject result = ConsultaEstadoCuenta.obtenerConsultaEstadoCuenta("SV", "702",
                "20230120", "jndi/SimacSV", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario",
                "600831, 600831, 600831", "P",
                "65163", "", "", "ESTCTASV",
                "http://192.168.151.205:3020/credisimanRESTWS/cards/getfile", "", "");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerDatosClientePrivadaWebServiceOk2() {

        XmlObject result = ConsultaEstadoCuenta.obtenerConsultaEstadoCuenta("SV", "70565154151",
                "20230120", "jndi/SimacSV", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario",
                "600831, 600831, 600831", "P",
                "65163541", "", "", "ESTCTASV",
                "http://192.168.151.205:3020/credisimanRESTWS/cards/getfile",
                "",
                "");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void obtenerDatosClienteVisaOk2() {

        XmlObject result = ConsultaEstadoCuenta.obtenerConsultaEstadoCuenta("SV", "536753",
                "20230210", "jndi/SimacSV", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario",
                "600831, 600831, 600831", "V", "006813574", "", "ORIONREPOSV", "",
                "",
                "",
                "");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }
}
