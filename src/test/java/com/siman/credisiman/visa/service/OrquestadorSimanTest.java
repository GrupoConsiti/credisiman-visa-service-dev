package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrquestadorSimanTest {
    private static final String NS = "http://siman.com/OrquestadorSiman";

    @Test
    public void orquestadorSimanObtenerListadoSucursales() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ListadoSucursales", "SV", null, null, null, null, null,
                "jdbc/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerDatosCliente() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ConsultaDatosCliente", "SV", "048382810", " ", " ", " ",
                " ", "jdbc/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "CDS00867", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerConsultaMovimientos() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ConsultaMovimientos",
                "SV", "6008314502274917", "20180101", "20221231", "P", null,
                "jdbc/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerConsultaSaldoMonedero() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ConsultaSaldoMonedero",
                "SV", "4000123456780000", "18140", "20211009", "P", "45738400",
                "jndi/SimacSV", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerListadoTarjetas() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ListadoTarjetas", "SV", "333214569",
                null, null, null, null,
                "jdbc/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanModificarLimiteTarjeta() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ConsultaPolizas", "SV", "4573840094950811", "V",
                null, null, null,
                "jdbc/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }
}
