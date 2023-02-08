package com.siman.credisiman.visa.service;

import static org.junit.Assert.assertEquals;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

public class ConsultaSaldoMonederoTest {
    public static final String NS = "http://siman.com/ConsultaSaldoMonedero";

    @Test
    public void obtenerDatosClienteOk() {
        XmlObject result = ConsultaSaldoMonedero.obtenerConsultaSaldoMonedero("SV", "4000123456780000", "18140",
                "20211009", "P", "45738400", "jndi/SimacSV", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());

//        assertEquals("000000050000", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:saldoInicial")[0]).getStringValue());
//        assertEquals("000000002000", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:puntosGanados")[0]).getStringValue());
//        assertEquals("000000010000", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:puntosCanjeados")[0]).getStringValue());
//        assertEquals("000000051000", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:saldoFinal")[0]).getStringValue());

    }
}
